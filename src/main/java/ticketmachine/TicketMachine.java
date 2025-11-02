package ticketmachine;

public class TicketMachine {
	private final int price;
	private int balance;
	private int total;

	public TicketMachine(int ticketCost) {
		if (ticketCost < 0) {
			throw new IllegalArgumentException("Ticket price cannot be negative");
		}
		this.price = ticketCost;
		this.balance = 0;
		this.total = 0;
	}

	public int getPrice() {
		return price;
	}

	public int getTotal() {
		return total;
	}

	public int getBalance() {
		return balance;
	}

	public void insertMoney(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Impossible d'insérer un montant négatif");
		}
		balance += amount;
	}

	public int refund() {
		int refundAmount = balance;
		balance = 0;
		System.out.println("Je vous rends : " + refundAmount + " centimes");
		return refundAmount;
	}

	public boolean printTicket() {
		if (balance < price) {
			System.out.println("Fonds insuffisants pour imprimer le ticket.");
			return false;
		}

		System.out.println("##################");
		System.out.println("# The BlueJ Line");
		System.out.println("# Ticket");
		System.out.println("# " + price + " cents.");
		System.out.println("##################");
		System.out.println();

		balance -= price;
		total += price;

		return true;
	}
}
