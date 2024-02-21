
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Currency;
import org.example.ExchangeRate;
import org.example.Wallet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(ExchangeAppTest.TestChecker.class)
public class ExchangeAppTest {
    private ExchangeRate exchangeRate;
    private Wallet wallet;
    private static List<ReportObject> reportObjects = new ArrayList<>();
    ;
    @BeforeEach
    public void createMocks(){
        this.exchangeRate = new ExchangeRate();
        this.wallet = new Wallet("moe",0,Currency.USD);
    }
    @Test
    public void addAmountUnitTest(){
        Random random = new Random();
        double amount = random.nextDouble(1,100000);
        Currency walletCurrency = Currency.USD;
        Currency convertedCurrency = Currency.TND;
        wallet.addAmount(amount, walletCurrency,exchangeRate);
        assertEquals(wallet.getAmount(convertedCurrency,exchangeRate),amount* exchangeRate.getExchangeRate(walletCurrency.name(),convertedCurrency.name()));
    }
    @Test
    public void compareXLS(){

        String filePath = "C:\\Users\\lenovo\\Downloads\\curency.xlsx";
        try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1 ; rowIndex <= sheet.getLastRowNum() ; rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                String baseCurrency = row.getCell(0).getStringCellValue();
                String targetCurrency = row.getCell(1).getStringCellValue();
                double exchangeValue = row.getCell(2).getNumericCellValue();
                System.out.println(exchangeRate.getExchangeRate(baseCurrency, targetCurrency));
                assertEquals(exchangeValue, exchangeRate.getExchangeRate(baseCurrency, targetCurrency));
            }
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterAll
    public static void afterAll() throws Exception{
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                float lineHeight = 18;

                for (ReportObject reportObject : reportObjects) {
                    String line = "Status: " + reportObject.getStatus() +
                            " | Method Name: " + reportObject.getMethodName()  +
                            " | Error Message: " + reportObject.getErrorMessage()  ;

                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -lineHeight);
                }

                contentStream.endText();
            }

            document.save("C:\\Users\\lenovo\\Downloads\\report.pdf");
            System.out.println("PDF generated successfully.");

            // SlackIntegration.sendMessage("C'est génial, votre test est fait de manière parfaite. :white_check_mark:", document);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class TestChecker implements AfterTestExecutionCallback {
        @Override
        public void afterTestExecution(ExtensionContext context) {

            int statusCode;
            String errorMessage;

            if (context.getExecutionException().isPresent()) {
                // Test failed
                statusCode = 500;
                errorMessage = context.getExecutionException().get().getMessage(); // Get the error message
            } else {
                // Test passed
                statusCode = 200;
                errorMessage = null;
            }
            String methodName = context.getRequiredTestMethod().getName();
            ReportObject reportObject = new ReportObject(statusCode, errorMessage, methodName);
            reportObjects.add(reportObject);
        }
    }



}
