package nattrn2;

public class TestData {

    public static void setupBank (BankLogic bank) {
        // Skapa kunder
        bank.createCustomer("Anna", "Andersson", "850101-1234");
        bank.createCustomer("Kalle", "Karlsson", "900202-5678");
        bank.createCustomer("Lisa", "Svensson", "920303-1111");

        // Skapa konton för Anna
        int acc1 = bank.createSavingsAccount("850101-1234");
        int acc2 = bank.createCreditAccount("850101-1234");

        // Skapa konton för Kalle
        int acc3 = bank.createSavingsAccount("900202-5678");

        // Skapa konton för Lisa
        int acc4 = bank.createCreditAccount("920303-1111");

        // 💰 5. (valfritt) sätt in pengar så du kan testa direkt
        bank.deposit("850101-1234", acc1, 1000);
        bank.deposit("850101-1234", acc2, 500);
        bank.deposit("900202-5678", acc3, 2000);
    }
}
