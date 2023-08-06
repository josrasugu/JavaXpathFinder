package com.dom.app.DomElementFinder.Controller;
import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dom.app.DomElementFinder.Models.Xpath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class xpathController {
    public static WebDriver driver;
    public static WebElement fetchedItem;

    public static boolean checkXPath(String xpath) {
        Duration durationTime = Duration.of(100, ChronoUnit.MILLIS);
        System.out.println(durationTime.getSeconds());
        try {
            fetchedItem = new WebDriverWait(driver, durationTime).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String XPathFetchHTML() {
        try {
            return fetchedItem.getAttribute("outerHTML").replace(" ", "");
        } catch (Exception e) {
            return "";
        }
    }

    public static String XPathFetchText() {
        try {
            return fetchedItem.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static List<String> checkCodeDifference(String firstCode, String secondCode) {
        List<String> resultLines = new ArrayList<>();
        List<String> referenceLines = Arrays.asList(firstCode.split("\n"));
        List<String> testLines = Arrays.asList(secondCode.split("\n"));
        Patch<String> patch = DiffUtils.diff(referenceLines, testLines);
        if (patch.getDeltas().isEmpty()) {
            System.out.println("-H-H-H-");
        } else {
            List<String> patchLines =
                    UnifiedDiffUtils.generateUnifiedDiff(
                            "", "", referenceLines, patch, 3);
//            return StringUtils.join(patchLines, "\n");
/*            for (String sp : patchLines) {
                System.out.println(sp);
            }*/
            resultLines = patchLines;
        }

        // Add the necessary import and implement the diff method here
        // Example implementation: https://github.com/java-diff-utils/java-diff-utils
        return resultLines;
    }

    public static double checkWordDifference(String firstWord, String secondWord) {
//        To check and confirm if it is working perfect
        int intersection = 0;
        int union = 0;

        char[] set1 = firstWord.toCharArray();
        char[] set2 = secondWord.toCharArray();

        for (char ch : set1) {
            if (secondWord.indexOf(ch) >= 0) {
                intersection++;
            }
        }

        union = set1.length + set2.length - intersection;
        return (double) (intersection * 100) / union;
    }

    public static String[] removeLastElement(String[] array) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, newArray.length);
        return newArray;
    }

    public static String[] removeFirstElement(String[] array) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, newArray.length);
        return newArray;
    }


    public static void xpathProcess() {
        String mysqlUrl = "jdbc:mysql://localhost:3306/ui_changes_tracking";
        String user = "root";
        String password = "";
        String mappedXPath = "/html/body/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[3]/a";
        Xpath dbResult = new Xpath();
        String SQL_SELECT = "Select * from Xpath";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(mysqlUrl, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                    long id = resultSet.getLong("id");
                    String path = resultSet.getString("path");
                    String pathOuterHtml = resultSet.getString("path_outer_html");
                    String pathText = resultSet.getString("path_text");

                dbResult.setId(id);
                dbResult.path(path);
                dbResult.pathOuterHtml(pathOuterHtml);
                dbResult.pathText(pathText);
                    // Timestamp -> LocalDateTime
                }
            System.out.println(dbResult.pathText);
//            System.out.println("Connected to the database!!!");
        } catch (Exception e) {
            System.out.println("Connection issue :-(");
        }

        System.setProperty("webdriver.chrome.driver", "C:/projects/aws-products/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.get("http://localhost/bpo_ticketing/dashboard.php?content=report&report=all");
/*
        if (checkXPath(mappedXPath)) {
            String XPathInnerHTML = XPathFetchHTML(mappedXPath);
            String XPathText = XPathFetchText(mappedXPath);
            System.out.println("XPATH Found");
        } else {
            System.out.println("XPATH Not Found");
        }
*/
        String davedPath = dbResult.path;
        String[] splitPath = davedPath.split("/");
        String[] fArray = davedPath.split("/");
        String[] bArray = davedPath.split("/");
        System.out.println(davedPath);

        int s = 0;
        int entryPoint = 0;
        for (String sp : splitPath) {
            if (sp.equals("")) {
                entryPoint = s;
            }
            s++;
        }

        List<String> forwardPath = new ArrayList<>();
        List<String> backPath = new ArrayList<>();
        int m = 0;
        while (m <= (splitPath.length - entryPoint - 1)) {
            try {
                String[] lastItemSplit = fArray[fArray.length - 1].split("\\[");
                if (lastItemSplit.length == 2) {
                    int lit = 0;
                    while (lit <= 5) {
                        String newItemChar = lastItemSplit[0] + "[" + lit + "]";
                        fArray[fArray.length - 1] = newItemChar;
                        forwardPath.add(String.join("/", fArray));
                        lit++;
                    }
                } else {
                    forwardPath.add(String.join("/", fArray));
                }
            } catch (Exception e) {
                forwardPath.add(String.join("/", fArray));
            }
            fArray = removeLastElement(fArray);
            if (m == 0) {
                int n = 0;
                while (n <= (entryPoint + 1)) {
                    bArray = removeFirstElement(bArray);
                    if (n == (entryPoint + 1)) {
                        backPath.add("//*/" + String.join("/", bArray));
                    }
                    n++;
                }
            } else {
                if (bArray.length > 1) {
                    bArray = removeFirstElement(bArray);
                    try {
                        String[] lastItemSplit = bArray[0].split("\\[");
                        if (lastItemSplit.length == 2) {
                            int lit = 0;
                            while (lit <= 5) {
                                String newItemChar = lastItemSplit[0] + "[" + lit + "]";
                                bArray[0] = newItemChar;
                                backPath.add("//*/" + String.join("/", bArray));
                                lit++;
                            }
                        } else {
                            backPath.add("//*/" + String.join("/", bArray));
                        }
                    } catch (Exception e) {
                        backPath.add("//*/" + String.join("/", bArray));
                    }
                }
            }
            m++;
        }

        List<Double> xpfPathScore = new ArrayList<>();
        List<Double> xpbPathScore = new ArrayList<>();

        String[] savedHTMLLines = dbResult.pathOuterHtml.strip().split("\\n");
        int xpfCount = 0;
        for (String xpf : forwardPath) {
            String[] passedHTMLLines = XPathFetchHTML().split("\\n");
            if (checkXPath(xpf)) {
                double pathScore = ((double) (forwardPath.size() - xpfCount) / forwardPath.size()) * 30;
                double htmlScore = 0;
                List<String> codeDiff = checkCodeDifference(XPathFetchHTML().strip(), dbResult.pathOuterHtml.strip());
                if (codeDiff != null) {
                    int diffCount = 0;
                    for (String line : codeDiff) {
                        if (line.trim().startsWith("+") || line.trim().startsWith("-")) {
                            diffCount++;
                        }
                    }
                    if ((diffCount - 2) < 100) {
                        htmlScore = 50 - (((double) (diffCount - 2) / (savedHTMLLines.length + passedHTMLLines.length)) * 50);
                    } else {
                        htmlScore = 0;
                    }
                } else {
                    htmlScore = 50;
                }

                double wordDiff = checkWordDifference(XPathFetchText(), dbResult.pathText);
                xpfPathScore.add(pathScore + htmlScore + wordDiff);
//                System.out.println("XPATH Found");
//                System.out.println(pathScore);
//                System.out.println(htmlScore);
//                System.out.println(wordDiff);
            } else {
                xpfPathScore.add(0.0);
//                System.out.println("XPATH Not Found");
            }
            xpfCount++;
        }

        int xpbCount = 0;
        for (String xpb : backPath) {
            String[] passedHTMLLines = XPathFetchHTML().split("\\n");
            if (checkXPath(xpb)) {
                double pathScore = ((double) (forwardPath.size() - xpbCount) / forwardPath.size()) * 30;
                double htmlScore = 0;
                List<String> codeDiff = checkCodeDifference(XPathFetchHTML().strip(), dbResult.pathOuterHtml.strip());
                if (codeDiff != null) {
                    int diffCount = 0;
                    for (String line : codeDiff) {
                        if (line.trim().startsWith("+") || line.trim().startsWith("-")) {
                            diffCount++;
                        }
                    }
                    if ((diffCount - 2) < 100) {
                        htmlScore = 50 - (((double) (diffCount - 2) / (savedHTMLLines.length + passedHTMLLines.length)) * 50);
                    } else {
                        htmlScore = 0;
                    }
                } else {
                    htmlScore = 50;
                }
                double wordDiff = checkWordDifference(XPathFetchText(), dbResult.pathText);
                xpbPathScore.add(pathScore + htmlScore + wordDiff);
//                System.out.println("XPATH Found");
            } else {
                xpbPathScore.add(0.0);
//                System.out.println("XPATH Not Found");
            }
            xpbCount++;
        }

        List<String> joinedPath = new ArrayList<>();
        joinedPath.addAll(forwardPath);
        joinedPath.addAll(backPath);

        List<Double> joinedScore = new ArrayList<>();
        joinedScore.addAll(xpfPathScore);
        joinedScore.addAll(xpbPathScore);

//         System.out.println(joinedPath);
//         System.out.println(joinedScore);
        Double largest = joinedScore.get(0);
        int finalIndex = 0;

        int aIndex = 0;
        for (Double i : joinedScore) {
            if (i > largest) {
                largest = i;
                finalIndex = aIndex;
            }
            aIndex++;
        }

        // Printing the joinedScore array and finalIndex
        for (Double score : joinedScore) {
            System.out.print(score + " ");
        }

        Duration durationTime = Duration.of(100, ChronoUnit.MILLIS);
        WebDriverWait wait = new WebDriverWait(driver, durationTime);
        WebElement searchItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(joinedPath.get(finalIndex))));
        searchItem.click();
    }
}
