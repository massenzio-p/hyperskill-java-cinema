package cinema;

import java.util.Scanner;

public class Cinema {

    private static final int SMALL_ROOM_SIZE_THRESHOLD = 60;
    private static final int SMALL_ROOM_SEAT_PRICE = 10;
    private static final int LARGE_ROOM_FRONT_HALF_SEAT_PRICE = 10;
    private static final int LARGE_ROOM_BACK_HALF_SEAT_PRICE = 8;

    private static final int SHOW_THE_SEATS_COMMAND = 1;
    private static final int BUY_A_TICKET_COMMAND = 2;
    private static final int STATISTICS_COMMAND = 3;
    private static final int EXIT_COMMAND = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatPerRow = scanner.nextInt();
        int[][] roomMatrix = new int[rows][seatPerRow];
        int resultProfit = 0;

        boolean breakLoop = false;
        do {
            System.out.println();
            System.out.println("1. Show the seats" +
                    "\n2. Buy a ticket" +
                    "\n3. Statistics" +
                    "\n0. Exit");
            int command = scanner.nextInt();
            System.out.println();
            switch (command) {
                case EXIT_COMMAND:
                    breakLoop = true;
                    break;
                case SHOW_THE_SEATS_COMMAND:
                    printRoom(roomMatrix);
                    break;
                case BUY_A_TICKET_COMMAND:
                    resultProfit += buyATicketRoutine(scanner, roomMatrix);
                    break;
                case STATISTICS_COMMAND:
                    showStatistics(roomMatrix, resultProfit);
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        } while (!breakLoop);
    }

    private static void showStatistics(int[][] roomMatrix, int resultProfit) {
        int purchasedTickets = calcPurchasedTickets(roomMatrix);
        float percentage = purchasedTickets * 100.0f / (roomMatrix.length * roomMatrix[0].length);
        System.out.printf(
                "Number of purchased tickets: %d" +
                        "%nPercentage: %.2f%%" +
                        "%nCurrent income: $%d" +
                        "%nTotal income: $%d%n",
                purchasedTickets,
                percentage,
                resultProfit,
                calculateFullProfit(roomMatrix.length, roomMatrix[0].length));
    }

    private static int calcPurchasedTickets(int[][] resultProfit) {
        int ticketsSum = 0;
        for (int[] ints : resultProfit) {
            for (int anInt : ints) {
                if (anInt == 1) ticketsSum++;
            }
        }
        return ticketsSum;
    }

    private static int buyATicketRoutine(Scanner scanner, int[][] roomMatrix) {
        boolean inputIsValid = false;
        int ticketRow = -1;
        int ticketSeat = -1;
        do {
            System.out.println("Enter a row number:");
            ticketRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            ticketSeat = scanner.nextInt();

            if (ticketRow > roomMatrix.length || ticketSeat > roomMatrix[0].length) {
                System.out.println("\nWrong input!\n");
            } else if (roomMatrix[ticketRow - 1][ticketSeat - 1] == 1) {
                System.out.println("\nThat ticket has already been purchased\n");
            } else {
                inputIsValid = true;
            }
        } while (!inputIsValid);

        int price = calcTicketPrice(ticketRow, roomMatrix);
        System.out.printf("%nTicket price: $%d%n", price);
        roomMatrix[ticketRow - 1][ticketSeat - 1] = 1;

        return price;
    }

    private static int calcTicketPrice(int ticketRow, int[][] roomMatrix) {
        int rows = roomMatrix.length;
        int seatsPerRow = roomMatrix[0].length;
        int total = rows * seatsPerRow;

        if (total <= SMALL_ROOM_SIZE_THRESHOLD) {
            return SMALL_ROOM_SEAT_PRICE;
        } else {
            return ticketRow <= rows / 2 ?
                    LARGE_ROOM_FRONT_HALF_SEAT_PRICE : LARGE_ROOM_BACK_HALF_SEAT_PRICE;
        }
    }

    private static int calculateFullProfit(int rows, int seats) {
        int total = rows * seats;
        if (total <= SMALL_ROOM_SIZE_THRESHOLD) {
            return SMALL_ROOM_SEAT_PRICE * total;
        } else {
            int largeRoomHalfBoundary = rows / 2;
            int sum = seats * largeRoomHalfBoundary * LARGE_ROOM_FRONT_HALF_SEAT_PRICE;
            sum += seats * (rows - largeRoomHalfBoundary) * LARGE_ROOM_BACK_HALF_SEAT_PRICE;
            return sum;
        }
    }

    private static void printRoom(int[][] roomMatrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cinema: \n");
        addHeaderString(sb, roomMatrix[0].length);

        for (int i = 0; i < roomMatrix.length; i++) {
            sb.append(i + 1);
            for (int j = 0; j < roomMatrix[0].length; j++) {
                sb.append(" ");
                if (roomMatrix[i][j] == 1) {
                    sb.append("B");
                } else {
                    sb.append("S");
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private static void addHeaderString(StringBuilder sb, int length) {
        sb.append("  ");
        for (int i = 1; i <= length; i++) {
            sb.append(i);
            if (i < length) {
                sb.append(" ");
            }
        }
        sb.append("\n");
    }
}