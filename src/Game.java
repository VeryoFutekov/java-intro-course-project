import elements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private List<Company> companies;
    private Scanner sc;
    private Board board;

    public Game(Scanner sc, List<Company> companies,List<Player> players) {
        this.players = players;
        this.companies = companies;
        this.sc = sc;
        this.board = new Board(companies);
    }

    public void startNewGame() {

        while(true) {

            this.players.forEach(player -> {
                player.setCurrentPosition(0);
                player.setRoundEarnings(0);
                player.clearTraps();
                player.clearCompaniesInvested();
                Steal steal = new Steal();
                int random = new Random().nextInt(3);
                if(random == 0) {
                    steal.setType(StealType.THE_BIG_BANK_ROBBERY);
                }
                if(random == 1) {
                    steal.setType(StealType.HOSTAGES);
                }
                if(random == 2) {
                    steal.setType(StealType.WORLD_CONQUERING);
                }
                player.setRoundSteal(steal);
                System.out.println(player);
            });
            this.playRound();
            long activePlayers = this.players.stream().filter(player -> player.getChocolateMoney() > 0).count();
            if(activePlayers == 1) {
                System.out.println(this.players.stream().findAny() + " Спечели играта!");
                break;
            }
            this.players.forEach(player -> {
                player.getActivatedTraps().forEach(trap -> trap.specialEffect(player));
                player.getCompaniesInvested().forEach(company -> company.payInvestments(player));
                player.setChocolateMoney(player.getRoundEarnings());
                if(player.getChocolateMoney() <= 0) {
                    player.setActive(false);
                }
            });
            activePlayers = this.players.stream().filter(player -> player.getChocolateMoney() > 0).count();
            if(activePlayers == 1) {
                System.out.println(this.players.stream().findAny() + " Спечели играта!");
                break;
            }
            this.board.shuffleFields();
            this.board.getElements().forEach(field -> {
                if(field instanceof Trap) {
                    ((Trap) field).setActive(false);
                    ((Trap) field).setType(null);
                }
                if(field instanceof Steal) {
                    ((Steal) field).setActive(false);
                }
            });
        }

    }

    private void playRound() {

        while(true) {
            this.printBoard();

            System.out.println();
            this.players.forEach(player -> {
                if(player.getCurrentPosition() < 19) {
                    System.out.println(player + " ход");
                    int dice = this.rollDice(2);
                    System.out.println("Играч с id" + player.getId() + " изкара " + dice);
                    if (player.getCurrentPosition() + dice > 19) {
                        player.setCurrentPosition(19);
                        System.out.println("и е на позиция " + player.getCurrentPosition());
                    }
                    else {
                        player.setCurrentPosition(player.getCurrentPosition() + dice);
                        System.out.println(" и е на позиция: " + player.getCurrentPosition());
                        Element currentElement = this.board.getElements().get(player.getCurrentPosition());
                        if (currentElement instanceof Trap) {

                            if ( ((Trap) currentElement).isActive() ) {
                                System.out.println("Ти активира капан!");
                                player.addTrap(((Trap) currentElement));
                                ((Trap) currentElement).setActive(false);
                            }
                            else {
                                if ( player.getActivatedTraps().stream().anyMatch(trap -> trap.getType().equals(TrapType.PROPAGANDA)) ) {
                                    System.out.println("Вече си активирал тази опция, опитай с друга");
                                }
                                else if (player.getChocolateMoney() < 50) {
                                    System.out.println("Намаш достатъчно пари, избери друга опция");
                                }
                                else {
                                    System.out.println("Желаете ли да заложите капан?");
                                    System.out.println("(N) : Не благодаря,не вярвам в злото");
                                    System.out.println("(1) : Проглеждане (50 пари)");
                                    if (player.getChocolateMoney() >= 100) {
                                        System.out.println("(2) : Данъчна ревизия (100 пари)");
                                        System.out.println("(3) : Пропаганда (100 пари)");
                                        System.out.println("(4) : Хазартен шеф (100 пари)");
                                    }
                                    if (player.getChocolateMoney() >= 200) {
                                        System.out.println("(5) : Развод по котешки (200 пари)");
                                    }
                                    String input = sc.next();
                                    switch(input) {

                                        case "N": System.out.println("Вие не заложихте капан и приключихте хода си!");
                                            break;
                                        case "1": System.out.println("Вие заложихте капан проглеждане!");
                                            player.setChocolateMoney(player.getChocolateMoney() - 50);
                                            ((Trap) currentElement).setType(TrapType.BROWSING);
                                            ((Trap) currentElement).setActive(true);
                                            break;
                                        case "2": if(player.getChocolateMoney() >= 100) {
                                            System.out.println("Вие заложихте данъчна ревизия!");
                                            player.setChocolateMoney(player.getChocolateMoney() - 100);
                                            ((Trap) currentElement).setType(TrapType.TAX_AUDIT);
                                            ((Trap) currentElement).setActive(true);
                                        }
                                            break;
                                        case "3": if(player.getChocolateMoney() >= 100) {
                                            System.out.println("Вие заложихте капан Пропаганда!");
                                            player.setChocolateMoney(player.getChocolateMoney() - 100);
                                            ((Trap) currentElement).setType(TrapType.PROPAGANDA);
                                            ((Trap) currentElement).setActive(true);
                                        }
                                            break;
                                        case "4": if(player.getChocolateMoney() >= 100) {
                                            System.out.println("Вие заложихте капан Хазартен бос!");
                                            player.setChocolateMoney(player.getChocolateMoney() - 100);
                                            ((Trap) currentElement).setType(TrapType.GAMBLING_BOSS);
                                            ((Trap) currentElement).setActive(true);
                                        }
                                            break;
                                        case "5": if(player.getChocolateMoney() >= 200) {
                                            System.out.println("Вие заложихте капан развод по котешки!");
                                            player.setChocolateMoney(player.getChocolateMoney() - 200);
                                            ((Trap) currentElement).setType(TrapType.CAT_DIVORCE);
                                            ((Trap) currentElement).setActive(true);
                                        }
                                            break;
                                    }
                                }
                            }
                        }
                        if (currentElement instanceof Chance) {

                            if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.WORLD_CONQUERING)) {
                                player.setRoundEarnings(player.getRoundEarnings() + 100);
                                System.out.println("player" + player.getId() + " получи elements.Steal бонус");
                            }
                            int dice2 = this.rollDice(10);
                            int dice3 = this.rollDice(100);
                            if (dice2 % 2 == 0) {
                                if ( player.getActivatedTraps().stream().anyMatch(trap -> trap.getType().equals(TrapType.GAMBLING_BOSS)) ) {
                                    System.out.println("Вече сте активирали тази опция, изберете друга.");
                                    player.removeHazartenBosTrap();
                                }
                                else {
                                    System.out.println("Щастливият ви ден!");
                                    if (dice3 <= 39) {
                                        System.out.println("Осиновявате група\r\n" +
                                                "самотните, за да си вдигнете\r\n" +
                                                "статуса ви.\r\n" +
                                                "Социалните мрежи са във\r\n" +
                                                "възторг, получавате\r\n" +
                                                "поощтрителни дарения от\r\n" +
                                                "обществото.");
                                        player.setRoundEarnings(player.getRoundEarnings() + 50);
                                    }
                                    if (dice3 <= 65 && dice3 >= 40) {
                                        System.out.println("Намери си секси мацка,\r\n" +
                                                "секси котка с големи\r\n" +
                                                "дарове. Получавате\r\n" +
                                                "вечното уважение на\r\n" +
                                                "кварталните батки, както\r\n" +
                                                "и чутовен статус на\r\n" +
                                                "***** разбивач.");
                                        player.setRoundEarnings(player.getRoundEarnings() + 100);
                                    }
                                    if (dice3 <= 79 && dice3 > 65) {
                                        System.out.println("Напускате университета и\r\n" +
                                                "ставате милионер. Честито!");
                                        player.setRoundEarnings(player.getRoundEarnings() + 150);
                                    }
                                    if (dice3 <= 94 && dice3 >= 80) {
                                        System.out.println("Младежи демонстрират\r\n" +
                                                "невероятна идея за\r\n" +
                                                "рационализиране на\r\n" +
                                                "производствените\r\n" +
                                                "мощности. Получавате\r\n" +
                                                "голям бонус.");
                                        player.setRoundEarnings(player.getRoundEarnings() + 200);
                                    }
                                    if (dice3 <= 100 && dice3 >= 95) {
                                        System.out.println("Наемате елф за личен\r\n" +
                                                "асистент,хората са\r\n" +
                                                "уверени че междувидовата\r\n" +
                                                "сегрегация е в историята.\r\n" +
                                                "Уважението към вас е\r\n" +
                                                "безгранично.");
                                        player.setRoundEarnings(player.getRoundEarnings() + 250);
                                    }
                                }
                            }
                            else {
                                System.out.println("Ти дръпна късата клечка!");
                                if (dice3 <= 39) {
                                    System.out.println("Вдигате толкова голям купон, че\r\n" +
                                            "не знаете къде се намирате на\r\n" +
                                            "следващата седмица. С мъка\r\n" +
                                            "установявате, че телевизорът Ви е\r\n" +
                                            "откраднат.");
                                    player.setChocolateMoney(player.getChocolateMoney() - 50);
                                }
                                if (dice3 <= 65 && dice3 >= 40) {
                                    System.out.println("Вие сте баща на три абитуриентки,\r\n" +
                                            "бъдете готови за стабилни\r\n" +
                                            "разходи.");
                                    player.setChocolateMoney(player.getChocolateMoney() - 100);
                                }
                                if (dice3 <= 79 && dice3 > 65) {
                                    System.out.println("Най-добрият Ви служител\r\n" +
                                            "получава повиквателна за\r\n" +
                                            "казармата. Губите обучен\r\n" +
                                            "персонал.");
                                    player.setChocolateMoney(player.getChocolateMoney() - 150);
                                }
                                if (dice3 <= 94 && dice3 >= 80) {
                                    System.out.println("На връщане от супермаркета,\r\n" +
                                            "чудовище се опитва да ви изяде.\r\n" +
                                            "Справяте се с неприятеля,\r\n" +
                                            "използвайки карате, но се налага\r\n" +
                                            "да пишете обяснения, които водят\r\n" +
                                            "до пропускане на важна среща и\r\n" +
                                            "загуба на бизнес партньор.");
                                    player.setChocolateMoney(player.getChocolateMoney() - 200);
                                }
                                if (dice3 <= 100 && dice3 >= 95) {
                                    System.out.println("Част от бизнесите Ви фалират,\r\n" +
                                            "заради задаваща се епидемия от\r\n" +
                                            "потна треска.");
                                    player.setChocolateMoney(player.getChocolateMoney() - 250);
                                }
                            }
                            if (player.getChocolateMoney() <= 0) {
                                System.out.println(player + "Загуби си парите!");
                                return;
                            }
                        }
                        if (currentElement instanceof Invest) {

                            if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.HOSTAGES)) {
                                player.setRoundEarnings(player.getRoundEarnings() + 100);
                                System.out.println("player" + player.getId() + " получи elements.Steal бонус");
                            }

                            System.out.println("Искате ли да инвестирате");
                            System.out.println("(N) : Не благодаря, не желая.");
                            for (int i = 0; i < this.companies.size(); i++) {
                                if(player.getChocolateMoney() > this.companies.get(i).getMinInvest()) {
                                    System.out.println(i + " : " + this.companies.get(i));
                                }
                            }

                            String input = sc.next();

                            if (input.equalsIgnoreCase("N")) {
                                System.out.println("Вие пропуснахте своя шанс да инвестирате!");
                            }
                            else if( (input.equals("0") || input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5"))
                                    && player.getChocolateMoney() > this.companies.get(Integer.parseInt(input)).getMinInvest()) {
                                System.out.println("Колко искаш да инвестираш" + " парите ти в момента: " + player.getChocolateMoney());
                                try {
                                    int invest = sc.nextInt();
                                    if(player.getChocolateMoney() > invest && invest >= this.companies.get(Integer.parseInt(input)).getMinInvest()) {
                                        System.out.println("Ти инвестира в " + this.companies.get(Integer.parseInt(input)));
                                        player.addCompany(this.companies.get(Integer.parseInt(input)));
                                        player.setChocolateMoney(player.getChocolateMoney() - invest);
                                    }
                                    else {
                                        System.out.println("Вие нямате достатъчно пари за да инвестирате в това!");
                                    }
                                }
                                catch(Exception e) {
                                    System.out.println("Грешен ход, вие пропуснахте да инвестирате!");
                                }

                            }
                            else {
                                System.out.println("Грешен ход, вие пропуснахте да инвестирате!");
                            }
                        }
                        if (currentElement instanceof PartyHard) {
                            player.setChocolateMoney(player.getChocolateMoney() - 25);
                            if (player.getChocolateMoney() <= 0) {
                                System.out.println(player + "Загуби всичките си пари!");
                                return;
                            }
                        }
                        if (currentElement instanceof Steal) {

                            if( !((Steal) currentElement).isActive() && !player.isStealActive()) {
                                ((Steal) currentElement).setActive(true);
                                player.setStealActive(true);
                            }
                            if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.THE_BIG_BANK_ROBBERY)) {
                                player.setRoundEarnings(player.getRoundEarnings() + 100);
                                System.out.println("player" + player.getId() + " получи elements.Steal бонус");
                            }
                        }
                    }
                }
            });
            long playersPlayingTheRound = this.players.stream().filter(player -> player.getCurrentPosition() < 19).count();
            if(playersPlayingTheRound == 0) {
                System.out.println("Рунда приключи!");
                break;
            }
        }
    }



    public void addPlayer(Player player) {
        this.players.add(player);
    }

    private int rollDice (int maxDiceValue) {
        return new Random().nextInt(maxDiceValue) + 1;
    }

    public void printBoard() {
        String res = String.format("%s%s%s%s%s%s%s%s\n" +
                        "%s%s%s\n" +
                        "%s%s%s\n" +
                        "%s%s%s%s%s%s%s%s", this.board.getElements().get(10), this.board.getElements().get(11), this.board.getElements().get(12), this.board.getElements().get(13)
                , this.board.getElements().get(14), this.board.getElements().get(15), this.board.getElements().get(16), this.board.getElements().get(17),
                this.board.getElements().get(9), "    ".repeat(6), this.board.getElements().get(18),
                this.board.getElements().get(8), "    ".repeat(6), this.board.getElements().get(19),
                this.board.getElements().get(7),this.board.getElements().get(6),this.board.getElements().get(5),this.board.getElements().get(4),
                this.board.getElements().get(3), this.board.getElements().get(2), this.board.getElements().get(1),this.board.getElements().get(0));
        System.out.println(res);

    }



}
