package net.sepidan.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import net.sepidan.converter.PersianDateConverter;

/**
 * Persian Date Utilities
 * <p>
 * کلاس ابزار برای کار با تاریخ‌های شمسی شامل متدهای کمکی برای محاسبات، اعتبارسنجی و عملیات روزمره
 *
 * <p>ویژگی‌ها:</p>
 * <ul>
 *   <li>محاسبه اختلاف بین دو تاریخ</li>
 *   <li>افزودن یا کم کردن روز از تاریخ</li>
 *   <li>بررسی تاریخ امروز</li>
 *   <li>دریافت تاریخ شمسی امروز</li>
 *   <li>اعتبارسنجی تاریخ شمسی</li>
 * </ul>
 *
 * @author Sepidan Team (Shayan Davarzani [shayandavarzani@gmail.com])
 * @version 1.0.0
 * @category DateConverter
 * @package net.sepidan.util
 * @copyright Copyright (c) 2026 Sepidan (info@sepidan.net)
 * @license MIT License
 * @see net.sepidan.converter.PersianDateConverter
 * @since 1.0.0
 */
public final class PersianDateUtils {

    private PersianDateUtils() {
    }

    /**
     * بررسی اعتبار یک تاریخ شمسی
     *
     * @param year  سال شمسی
     * @param month ماه شمسی (۱ تا ۱۲)
     * @param day   روز شمسی (۱ تا ۳۱)
     * @return true اگر تاریخ معتبر باشد
     * @since 1.0.0
     */
    public static boolean isValidPersianDate(int year, int month, int day) {
        try {
            // سعی در ساخت تاریخ شمسی
            PersianDateConverter.getPersianCalendar(
                LocalDate.of(year + 621, month, day));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * محاسبه اختلاف بین دو تاریخ میلادی به روز
     *
     * @param start تاریخ شروع
     * @param end   تاریخ پایان
     * @return تعداد روزهای اختلاف
     * @since 1.0.0
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * محاسبه اختلاف بین دو تاریخ شمسی به روز
     *
     * @param startDateStr تاریخ شروع به صورت رشته (yyyy/MM/dd)
     * @param endDateStr   تاریخ پایان به صورت رشته (yyyy/MM/dd)
     * @return تعداد روزهای اختلاف
     * @throws IllegalArgumentException اگر تاریخ‌ها نامعتبر باشند
     * @since 1.0.0
     */
    public static long daysBetween(String startDateStr, String endDateStr) {
        LocalDate start = PersianDateConverter.toGregorian(startDateStr);
        LocalDate end = PersianDateConverter.toGregorian(endDateStr);
        return daysBetween(start, end);
    }

    /**
     * افزودن تعداد روز به یک تاریخ میلادی
     *
     * @param date تاریخ میلادی
     * @param days تعداد روز (مثبت برای آینده، منفی برای گذشته)
     * @return تاریخ جدید
     * @since 1.0.0
     */
    public static LocalDate addDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    /**
     * افزودن تعداد روز به یک تاریخ شمسی
     *
     * @param persianDateStr تاریخ شمسی به صورت رشته (yyyy/MM/dd)
     * @param days           تعداد روز (مثبت برای آینده، منفی برای گذشته)
     * @return تاریخ میلادی جدید
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static LocalDate addDays(String persianDateStr, long days) {
        LocalDate date = PersianDateConverter.toGregorian(persianDateStr);
        return addDays(date, days);
    }

    /**
     * بررسی اینکه آیا تاریخ میلادی امروز است
     *
     * @param date تاریخ میلادی
     * @return true اگر تاریخ امروز باشد
     * @since 1.0.0
     */
    public static boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    /**
     * بررسی اینکه آیا تاریخ شمسی امروز است
     *
     * @param persianDateStr تاریخ شمسی به صورت رشته (yyyy/MM/dd)
     * @return true اگر تاریخ امروز باشد
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static boolean isToday(String persianDateStr) {
        LocalDate date = PersianDateConverter.toGregorian(persianDateStr);
        return isToday(date);
    }

    /**
     * دریافت تاریخ شمسی امروز با فرمت پیش‌فرض
     *
     * @return تاریخ شمسی امروز به صورت yyyy/MM/dd
     * @since 1.0.0
     */
    public static String today() {
        return PersianDateConverter.toPersian(LocalDate.now());
    }

    /**
     * دریافت تاریخ شمسی امروز با فرمت دلخواه
     *
     * @param pattern الگوی فرمت (مثلاً yyyy/MM/dd یا yyyy-MM-dd)
     * @return تاریخ شمسی امروز فرمت شده
     * @since 1.0.0
     */
    public static String today(String pattern) {
        return PersianDateConverter.toPersian(LocalDate.now(), pattern);
    }

    /**
     * دریافت تاریخ میلادی امروز
     *
     * @return تاریخ میلادی امروز
     * @since 1.0.0
     */
    public static LocalDate todayGregorian() {
        return LocalDate.now();
    }

    /**
     * دریافت شروع سال شمسی (اول فروردین)
     *
     * @param persianYear سال شمسی
     * @return تاریخ میلادی معادل اول فروردین
     * @since 1.0.0
     */
    public static LocalDate getPersianYearStart(int persianYear) {
        return PersianDateConverter.toGregorian(persianYear, 1, 1);
    }

    /**
     * دریافت پایان سال شمسی (آخر اسفند)
     *
     * @param persianYear سال شمسی
     * @return تاریخ میلادی معادل آخر اسفند
     * @since 1.0.0
     */
    public static LocalDate getPersianYearEnd(int persianYear) {
        int lastMonth = 12;
        int lastDay = PersianDateConverter.getPersianMonthDays(persianYear, lastMonth);
        return PersianDateConverter.toGregorian(persianYear, lastMonth, lastDay);
    }

    /**
     * دریافت تعداد روزهای باقی‌مانده تا پایان سال شمسی
     *
     * @param persianDateStr تاریخ شمسی به صورت رشته (yyyy/MM/dd)
     * @return تعداد روزهای باقی‌مانده تا پایان سال
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static int daysUntilYearEnd(String persianDateStr) {
        LocalDate date = PersianDateConverter.toGregorian(persianDateStr);
        PersianDateConverter.PersianDateInfo info = PersianDateConverter.getPersianDateInfo(date);
        int totalDays = PersianDateConverter.getPersianYearDays(info.getYear());
        return totalDays - info.getDayOfYear();
    }

    /**
     * دریافت تعداد روزهای گذشته از ابتدای سال شمسی
     *
     * @param persianDateStr تاریخ شمسی به صورت رشته (yyyy/MM/dd)
     * @return تعداد روزهای گذشته از ابتدای سال
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static int daysSinceYearStart(String persianDateStr) {
        LocalDate date = PersianDateConverter.toGregorian(persianDateStr);
        PersianDateConverter.PersianDateInfo info = PersianDateConverter.getPersianDateInfo(date);
        return info.getDayOfYear() - 1;
    }
}
