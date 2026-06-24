package net.sepidan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import net.sepidan.converter.PersianDateConverter;
import net.sepidan.converter.PersianDateConverter.PersianDateInfo;
import net.sepidan.format.PersianDateFormatter;
import net.sepidan.util.PersianDateUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Persian Date Converter Test
 *
 * کلاس تست برای بررسی عملکرد کتابخانه تبدیل تاریخ
 * شامل تست‌های تبدیل میلادی به شمسی، شمسی به میلادی،
 * فرمت‌ها، اعتبارسنجی و محاسبات مختلف
 *
 * @category   DateConverter
 * @package    net.sepidan
 * @author     Sepidan Team
 * @copyright  Copyright (c) 2026 Sepidan (info@sepidan.net)
 * @license    MIT License
 * @version    1.0.0
 * @see        net.sepidan.converter.PersianDateConverter
 * @see        net.sepidan.format.PersianDateFormatter
 * @see        net.sepidan.util.PersianDateUtils
 * @since      1.0.0
 */
@DisplayName("تست‌های کتابخانه تبدیل تاریخ شمسی")
public class PersianDateConverterTest {

    private LocalDate testDate;
    private String testPersianDate;

    /**
     * تنظیمات اولیه قبل از هر تست
     */
    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2026, 6, 24);
        testPersianDate = "1405/04/03";
    }

    // ==================== تست‌های تبدیل میلادی → شمسی ====================

    /**
     * تست تبدیل تاریخ میلادی به شمسی با فرمت اعداد فارسی
     */
    @Test
    @DisplayName("تبدیل میلادی به شمسی با اعداد فارسی")
    void testConvertToPersian() {
        String result = PersianDateFormatter.formatWithPersianNumbers(testDate);
        assertEquals("۱۴۰۵/۰۴/۰۳", result);
    }

    /**
     * تست تبدیل تاریخ میلادی به شمسی با فرمت استاندارد
     */
    @Test
    @DisplayName("تبدیل میلادی به شمسی با فرمت استاندارد")
    void testConvertToPersianStandard() {
        String result = PersianDateConverter.toPersian(testDate);
        assertEquals("1405/04/03", result);
    }

    /**
     * تست تبدیل تاریخ میلادی به شمسی با فرمت خط تیره
     */
    @Test
    @DisplayName("تبدیل میلادی به شمسی با فرمت خط تیره")
    void testConvertToPersianWithDash() {
        String result = PersianDateFormatter.formatWithDash(testDate);
        assertEquals("1405-04-03", result);
    }

    /**
     * تست تبدیل تاریخ میلادی به شمسی با نام ماه
     */
    @Test
    @DisplayName("تبدیل میلادی به شمسی با نام ماه")
    void testConvertToPersianWithMonthName() {
        String result = PersianDateFormatter.formatWithPersianNumbersAndMonth(testDate);
        assertEquals("۱۴۰۵/۰۴/۰۳", result);
    }

    // ==================== تست‌های اطلاعات تاریخ ====================

    /**
     * تست دریافت نام روز هفته
     */
    @Test
    @DisplayName("دریافت نام روز هفته")
    void testWeekVariants() {
        String result = PersianDateConverter.getDayOfWeek(testDate);
        assertEquals("چهارشنبه", result);
    }

    /**
     * تست دریافت نام ماه
     */
    @Test
    @DisplayName("دریافت نام ماه")
    void testGetMonthName() {
        String result = PersianDateConverter.getMonthName(testDate);
        assertEquals("تیر", result);
    }

    /**
     * تست دریافت روز سال
     */
    @Test
    @DisplayName("دریافت روز سال")
    void testGetDayOfYear() {
        int result = PersianDateConverter.getDayOfYear(testDate);
        assertEquals(96, result);
    }

    /**
     * تست دریافت اطلاعات کامل تاریخ برای یک تاریخ خاص
     */
    @Test
    @DisplayName("دریافت اطلاعات کامل تاریخ برای تاریخ تولد")
    public void checkPassedDate() {
        LocalDate shayanBirthday = LocalDate.of(1990, 12, 26);
        PersianDateInfo info = PersianDateConverter.getPersianDateInfo(shayanBirthday);

        assertEquals(10, info.getMonth());
        assertEquals(1369, info.getYear());
        assertEquals(5, info.getDay());
        assertEquals(281, info.getDayOfYear());
        assertEquals("دی", info.getMonthName());
    }

    // ==================== تست‌های تقویم شمسی ====================

    /**
     * تست تشخیص سال کبیسه
     */
    @Test
    @DisplayName("تشخیص سال کبیسه")
    public void checkPersianLeapYear() {
        assertTrue(PersianDateConverter.isPersianLeapYear(1403));
    }

    /**
     * تست تعداد روزهای ماه‌های مختلف
     */
    @Test
    @DisplayName("تعداد روزهای ماه‌های شمسی")
    void testPersianMonthDays() {
        assertEquals(31, PersianDateConverter.getPersianMonthDays(1403, 1)); // فروردین
        assertEquals(31, PersianDateConverter.getPersianMonthDays(1403, 6)); // شهریور
        assertEquals(30, PersianDateConverter.getPersianMonthDays(1403, 7)); // مهر
        assertEquals(30, PersianDateConverter.getPersianMonthDays(1403, 11)); // بهمن
        assertEquals(29, PersianDateConverter.getPersianMonthDays(1402, 12)); // اسفند
    }

    /**
     * تست اعتبارسنجی تاریخ شمسی
     */
    @Test
    @DisplayName("اعتبارسنجی تاریخ شمسی")
    void testIsValidPersianDate() {
        assertTrue(PersianDateUtils.isValidPersianDate(1403, 1, 1));
        assertTrue(PersianDateUtils.isValidPersianDate(1403, 12, 29));
        // تاریخ نامعتبر
        assertTrue(!PersianDateUtils.isValidPersianDate(1403, 13, 1));
    }

    // ==================== تست‌های تبدیل شمسی → میلادی ====================

    /**
     * تست اختلاف بین دو تاریخ شمسی
     */
    @Test
    @DisplayName("محاسبه اختلاف بین دو تاریخ شمسی")
    public void assertDateDiff() {
        LocalDate start = PersianDateConverter.toGregorian("1405-04-01","yyyy-MM-dd");
        LocalDate end = PersianDateConverter.toGregorian("1405-05-01","yyyy-MM-dd");

        assertEquals(end, start.plusDays(31));
    }

    /**
     * تست تبدیل رفت و برگشت (دوطرفه)
     */
    @Test
    @DisplayName("تبدیل رفت و برگشت شمسی → میلادی → شمسی")
    public void differentKindOfConverting() {
        String initDateStr = "1369-10-05";
        LocalDate initDate = PersianDateConverter.toGregorian(initDateStr,"yyyy-MM-dd");
        String convertedStr = PersianDateFormatter.formatWithDash(initDate);

        assertEquals(initDateStr, convertedStr);
    }

    /**
     * تست تبدیل رشته تاریخ شمسی به میلادی
     */
    @Test
    @DisplayName("تبدیل رشته تاریخ شمسی به میلادی")
    void testToGregorianFromString() {
        LocalDate result = PersianDateConverter.toGregorian("1405/04/03");
        assertEquals(LocalDate.of(2026, 6, 24), result);
    }

    /**
     * تست تبدیل تاریخ شمسی به میلادی با فرمت مختلف
     */
    @Test
    @DisplayName("تبدیل تاریخ شمسی به میلادی با فرمت دلخواه")
    void testToGregorianWithPattern() {
        LocalDate result1 = PersianDateConverter.toGregorian("1405-04-03", "yyyy-MM-dd");
        LocalDate result2 = PersianDateConverter.toGregorian("1405/04/03", "yyyy/MM/dd");

        assertEquals(LocalDate.of(2026, 6, 24), result1);
        assertEquals(LocalDate.of(2026, 6, 24), result2);
    }

    // ==================== تست‌های PersianDateUtils ====================

    /**
     * تست اختلاف روز بین دو تاریخ
     */
    @Test
    @DisplayName("محاسبه اختلاف روز بین دو تاریخ")
    void testDaysBetween() {
        long days = PersianDateUtils.daysBetween("1405/01/01", "1405/01/31");
        assertEquals(30, days);
    }

    /**
     * تست افزودن روز به تاریخ
     */
    @Test
    @DisplayName("افزودن روز به تاریخ شمسی")
    void testAddDays() {
        LocalDate result = PersianDateUtils.addDays("1405/01/01", 30);
        assertEquals(PersianDateConverter.toGregorian("1405/01/31"), result);
    }

    /**
     * تست تاریخ امروز
     */
    @Test
    @DisplayName("دریافت تاریخ امروز")
    void testToday() {
        String today = PersianDateUtils.today();
        assertTrue(today != null && !today.isEmpty());
    }

    /**
     * تست شروع و پایان سال
     */
    @Test
    @DisplayName("دریافت شروع و پایان سال شمسی")
    void testYearStartEnd() {
        LocalDate start = PersianDateUtils.getPersianYearStart(1403);
        LocalDate end = PersianDateUtils.getPersianYearEnd(1403);

        assertEquals(PersianDateConverter.toGregorian(1403, 1, 1), start);
        assertEquals(PersianDateConverter.toGregorian(1403, 12, 30), end);
    }

    /**
     * تست روزهای باقی‌مانده تا پایان سال
     */
    @Test
    @DisplayName("روزهای باقی‌مانده تا پایان سال")
    void testDaysUntilYearEnd() {
        int days = PersianDateUtils.daysUntilYearEnd("1405/07/01");
        assertTrue(days > 0);
    }

    // ==================== تست‌های خطا ====================

    /**
     * تست خطا برای تاریخ شمسی نامعتبر
     */
    @Test
    @DisplayName("خطا برای تاریخ شمسی نامعتبر")
    void testInvalidPersianDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            PersianDateConverter.toGregorian(1403, 13, 1);
        });
    }

    /**
     * تست خطا برای رشته تاریخ نامعتبر
     */
    @Test
    @DisplayName("خطا برای رشته تاریخ نامعتبر")
    void testInvalidDateString() {
        assertThrows(IllegalArgumentException.class, () -> {
            PersianDateConverter.toGregorian("1403/13/01");
        });
    }

    /**
     * تست ورودی null
     */
    @Test
    @DisplayName("تست ورودی null")
    void testNullInput() {
        assertEquals(null, PersianDateConverter.toPersian((LocalDate) null));
        assertEquals(null, PersianDateConverter.toGregorian((String) null));
    }
}