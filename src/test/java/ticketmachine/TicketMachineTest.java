package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	@Test
	void cannotPrintTicketIfInsufficientFunds() {
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Le ticket ne doit pas s'imprimer si le solde est insuffisant");
	}

	// S4 : on imprime le ticket si le montant inséré est suffisant
	@Test
	void canPrintTicketIfEnoughMoney() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket devrait être imprimé si le solde est suffisant");
	}

	// S5 : quand on imprime un ticket la balance est décrémentée du prix du ticket
	@Test
	void balanceDecreasesAfterPrintingTicket() {
		machine.insertMoney(100);
		machine.printTicket();
		assertEquals(50, machine.getBalance(), "La balance doit être décrémentée du prix du ticket");
	}

	// S6 : le montant collecté est mis à jour quand on imprime un ticket
	@Test
	void totalIncreasesOnlyAfterTicketPrinted() {
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total ne doit pas augmenter avant impression");
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total doit augmenter après impression");
	}

	// S7 : refund() rend correctement la monnaie
	@Test
	void refundReturnsCorrectAmount() {
		machine.insertMoney(30);
		int refund = machine.refund();
		assertEquals(30, refund, "Refund ne renvoie pas le bon montant");
	}

	// S8 : refund() remet la balance à zéro
	@Test
	void refundResetsBalance() {
		machine.insertMoney(60);
		machine.refund();
		assertEquals(0, machine.getBalance(), "Refund ne remet pas la balance à zéro");
	}

	// S9 : on ne peut pas insérer un montant négatif
	@Test
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10),
				"Insertion d'un montant négatif doit lever une exception");
	}

	// S10 : on ne peut pas créer une machine avec un prix de ticket négatif
	@Test
	void cannotCreateMachineWithNegativePrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1),
				"Création d'une machine avec un prix négatif doit lever une exception");
	}

}
