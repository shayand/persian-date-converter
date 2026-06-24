package net.sepidan.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.engine.EpochDays;

/**
 * Persian Date Converter
 * <p>
 * کلاس اصلی برای تبدیل تاریخ میلادی به شمسی و برعکس با استفاده از کتابخانه Time4J و کلاس
 * PersianCalendar
 *
 * <p>ویژگی‌ها:</p>
 * <ul>
 *   <li>تبدیل میلادی به شمسی با فرمت‌های مختلف</li>
 *   <li>تبدیل شمسی به میلادی با اعتبارسنجی</li>
 *   <li>دریافت نام روز هفته و ماه به فارسی</li>
 *   <li>بررسی سال کبیسه و تعداد روزهای ماه</li>
 *   <li>دریافت شماره روز و هفته در سال</li>
 *   <li>قابل استفاده در MapStruct</li>
 *   <li>پشتیبانی از Recordها</li>
 *   <li>پشتیبانی از LocalDate، LocalDateTime، ZonedDateTime و Date</li>
 *   <li>کمترین تولید Garbage</li>
 *   <li>Thread-Safe</li>
 * </ul>
 *
 * <p>مثال استفاده:</p>
 * <pre>
 * // تبدیل میلادی به شمسی
 * LocalDate date = LocalDate.of(2026, 6, 24);
 * String persian = PersianDateConverter.toPersian(date);
 * // خروجی: 1405/04/03
 *
 * // تبدیل شمسی به میلادی
 * LocalDate gregorian = PersianDateConverter.toGregorian(1405, 4, 3);
 * // خروجی: 2026-06-24
 *
 * // دریافت اطلاعات کامل
 * PersianDateInfo info = PersianDateConverter.getPersianDateInfo(date);
 * System.out.println(info.toFullString());
 * // خروجی: چهارشنبه 3 تیر 1405
 * </pre>
 *
 * @author Sepidan Team (Shayan Davarzani [shayandavarzani@gmail.com])
 * @version 1.0.0
 * @category DateConverter
 * @package net.sepidan.converter
 * @copyright Copyright (c) 2026 Sepidan (info@sepidan.net)
 * @license MIT License
 * @see net.time4j.calendar.PersianCalendar
 * @see net.sepidan.format.PersianDateFormatter
 * @see net.sepidan.util.PersianDateUtils
 * @see net.sepidan.converter.PersianDateConverter.PersianDateInfo
 * @since 1.0.0
 */
public final class PersianDateConverter {

    /**
     * منطقه زمانی پیش‌فرض سیستم
     */
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * Locale فارسی برای نمایش نام روزها و ماه‌ها
     */
    private static final Locale PERSIAN_LOCALE = new Locale("fa", "IR");

    /**
     * اختلاف سال میلادی و شمسی (تقریبی) برای محاسبات ساده استفاده می‌شود
     */
    private static final int GREGORIAN_PERSIAN_OFFSET = 621;

    /**
     * نام ماه‌های شمسی به فارسی
     */
    private static final String[] PERSIAN_MONTHS = {
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    };

    /**
     * نام روزهای هفته به فارسی
     */
    private static final String[] PERSIAN_DAYS = {
        "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه",
    };

    /**
     * سازنده خصوصی برای جلوگیری از نمونه‌سازی این کلاس یک Utility Class است و نباید نمونه‌سازی شود
     *
     * @throws UnsupportedOperationException همیشه پرتاب می‌شود
     */
    private PersianDateConverter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ==================== تبدیل میلادی → شمسی (داخلی) ====================

    /**
     * تبدیل LocalDate به PersianCalendar
     *
     * <p>این متد جایگزین متد from() در PersianCalendar است که در نسخه‌های
     * جدید وجود ندارد. از مسیر PlainDate → EpochDays → PersianCalendar استفاده می‌کند.</p>
     *
     * @param gregorianDate تاریخ میلادی
     * @return شیء PersianCalendar معادل تاریخ شمسی
     * @since 1.0.0
     */
    private static PersianCalendar toPersianCalendar(LocalDate gregorianDate) {
        PlainDate plainDate = PlainDate.from(gregorianDate);
        long days = plainDate.getDaysSinceEpochUTC();
        return PersianCalendar.axis()
            .getCalendarSystem()
            .transform(days);
    }

    // ==================== تبدیل میلادی → شمسی ====================

    /**
     * تبدیل LocalDate میلادی به تاریخ شمسی با فرمت پیش‌فرض
     *
     * <p>فرمت پیش‌فرض: yyyy/MM/dd</p>
     *
     * @param gregorianDate تاریخ میلادی
     * @return تاریخ شمسی به صورت String با فرمت yyyy/MM/dd
     * @since 1.0.0
     */
    public static String toPersian(LocalDate gregorianDate) {
        return toPersian(gregorianDate, "yyyy/MM/dd");
    }

    /**
     * تبدیل LocalDate میلادی به تاریخ شمسی با فرمت دلخواه
     *
     * <p>فرمت‌های پشتیبانی شده:</p>
     * <ul>
     *   <li>yyyy/MM/dd - مثال: 1405/04/03</li>
     *   <li>yyyy-MM-dd - مثال: 1405-04-03</li>
     *   <li>yyyy MM dd - مثال: 1405 04 03</li>
     *   <li>dd/MM/yyyy - مثال: 03/04/1405</li>
     *   <li>dd-MM-yyyy - مثال: 03-04-1405</li>
     * </ul>
     *
     * @param gregorianDate تاریخ میلادی
     * @param pattern       الگوی فرمت
     * @return تاریخ شمسی فرمت شده
     * @throws IllegalArgumentException اگر الگوی فرمت نامعتبر باشد
     * @since 1.0.0
     */
    public static String toPersian(LocalDate gregorianDate, String pattern) {
        if (gregorianDate == null) {
            return null;
        }

        PersianCalendar persian = toPersianCalendar(gregorianDate);
        int year = persian.getYear();
        int month = persian.getMonth().getValue();
        int day = persian.getDayOfMonth();

        if (pattern == null || pattern.isEmpty()) {
            pattern = "yyyy/MM/dd";
        }

        return formatPersianDate(year, month, day, pattern);
    }

    /**
     * فرمت‌دهی تاریخ شمسی بر اساس الگوی مشخص
     *
     * @param year    سال شمسی
     * @param month   ماه شمسی (۱ تا ۱۲)
     * @param day     روز شمسی (۱ تا ۳۱)
     * @param pattern الگوی فرمت
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    private static String formatPersianDate(int year, int month, int day, String pattern) {
        switch (pattern) {
            case "yyyy/MM/dd":
                return String.format("%d/%02d/%02d", year, month, day);
            case "yyyy-MM-dd":
                return String.format("%d-%02d-%02d", year, month, day);
            case "yyyy MM dd":
                return String.format("%d %02d %02d", year, month, day);
            case "dd/MM/yyyy":
                return String.format("%02d/%02d/%d", day, month, year);
            case "dd-MM-yyyy":
                return String.format("%02d-%02d-%d", day, month, year);
            default:
                return String.format("%d/%02d/%02d", year, month, day);
        }
    }

    // ==================== تبدیل شمسی → میلادی ====================

    /**
     * تبدیل تاریخ شمسی به LocalDate میلادی
     *
     * @param persianYear  سال شمسی (مثلاً ۱۴۰۵)
     * @param persianMonth ماه شمسی (۱ تا ۱۲)
     * @param persianDay   روز شمسی (۱ تا ۳۱)
     * @return LocalDate معادل تاریخ میلادی
     * @throws IllegalArgumentException اگر تاریخ شمسی نامعتبر باشد
     * @see #toGregorian(String)
     * @see #toGregorian(String, String)
     * @since 1.0.0
     */
    public static LocalDate toGregorian(int persianYear, int persianMonth, int persianDay) {
        // اعتبارسنجی
        if (!isValidPersianDate(persianYear, persianMonth, persianDay)) {
            throw new IllegalArgumentException(
                String.format("Invalid Persian date: %d/%02d/%02d", persianYear, persianMonth,
                    persianDay)
            );
        }

        // 1. ساخت PersianCalendar
        PersianCalendar persian = PersianCalendar.of(persianYear, persianMonth, persianDay);

        // 2. تبدیل به روز (epoch days)
        long days = PersianCalendar.axis()
            .getCalendarSystem()
            .transform(persian);

        // 3. تبدیل روزها به LocalDate
        return PlainDate.of(days, EpochDays.UTC)
            .toTemporalAccessor();
    }

    /**
     * تبدیل رشته تاریخ شمسی به LocalDate میلادی
     *
     * <p>فرمت پیش‌فرض: yyyy/MM/dd</p>
     *
     * @param persianDateStr تاریخ شمسی به صورت "yyyy/MM/dd"
     * @return LocalDate معادل تاریخ میلادی
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @see #toGregorian(int, int, int)
     * @see #toGregorian(String, String)
     * @since 1.0.0
     */
    public static LocalDate toGregorian(String persianDateStr) {
        return toGregorian(persianDateStr, "yyyy/MM/dd");
    }

    /**
     * تبدیل رشته تاریخ شمسی به LocalDate میلادی با فرمت دلخواه
     *
     * @param persianDateStr تاریخ شمسی به صورت رشته
     * @param pattern        الگوی فرمت (مثلاً yyyy/MM/dd یا yyyy-MM-dd)
     * @return LocalDate معادل تاریخ میلادی
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static LocalDate toGregorian(String persianDateStr, String pattern) {
        if (persianDateStr == null || pattern == null) {
            return null;
        }

        // تشخیص جداکننده بر اساس الگو
        String separator = pattern.contains("/") ? "/" : "-";
        String[] parts = persianDateStr.split(separator);

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date format: " + persianDateStr);
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return toGregorian(year, month, day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date format: " + persianDateStr, e);
        }
    }

    // ==================== اعتبارسنجی تاریخ شمسی ====================

    /**
     * بررسی اعتبار تاریخ شمسی
     *
     * @param year  سال شمسی
     * @param month ماه شمسی (۱ تا ۱۲)
     * @param day   روز شمسی (۱ تا ۳۱)
     * @return true اگر تاریخ معتبر باشد
     * @since 1.0.0
     */
    public static boolean isValidPersianDate(int year, int month, int day) {
        if (year < 1 || year > 3000) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }

        int maxDay = getPersianMonthDays(year, month);
        return day >= 1 && day <= maxDay;
    }

    // ==================== دریافت اطلاعات تاریخ ====================

    /**
     * دریافت نام روز هفته به فارسی از تاریخ میلادی
     *
     * @param gregorianDate تاریخ میلادی
     * @return نام روز هفته به فارسی (شنبه، یکشنبه، ...)
     * @since 1.0.0
     */
    public static String getDayOfWeek(LocalDate gregorianDate) {
        PersianCalendar persian = toPersianCalendar(gregorianDate);
        int dayOfWeek = persian.getDayOfWeek().getValue(); // 1=شنبه, 7=جمعه
        return PERSIAN_DAYS[dayOfWeek];
    }

    /**
     * دریافت نام روز هفته به فارسی از تاریخ شمسی
     *
     * @param persianYear  سال شمسی
     * @param persianMonth ماه شمسی (۱ تا ۱۲)
     * @param persianDay   روز شمسی (۱ تا ۳۱)
     * @return نام روز هفته به فارسی
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static String getDayOfWeek(int persianYear, int persianMonth, int persianDay) {
        PersianCalendar persian = PersianCalendar.of(persianYear, persianMonth, persianDay);
        int dayOfWeek = persian.getDayOfWeek().getValue();
        return PERSIAN_DAYS[dayOfWeek - 1];
    }

    /**
     * دریافت نام ماه به فارسی از تاریخ میلادی
     *
     * @param gregorianDate تاریخ میلادی
     * @return نام ماه به فارسی (فروردین، اردیبهشت، ...)
     * @since 1.0.0
     */
    public static String getMonthName(LocalDate gregorianDate) {
        PersianCalendar persian = toPersianCalendar(gregorianDate);
        int month = persian.getMonth().getValue();
        return PERSIAN_MONTHS[month - 1];
    }

    /**
     * دریافت نام ماه به فارسی از شماره ماه
     *
     * @param month شماره ماه (۱ تا ۱۲)
     * @return نام ماه به فارسی
     * @throws IllegalArgumentException اگر ماه خارج از بازه باشد
     * @since 1.0.0
     */
    public static String getMonthName(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        return PERSIAN_MONTHS[month - 1];
    }

    /**
     * دریافت شماره روز در سال شمسی از تاریخ میلادی
     *
     * @param gregorianDate تاریخ میلادی
     * @return شماره روز از ۱ تا ۳۶۶
     * @since 1.0.0
     */
    public static int getDayOfYear(LocalDate gregorianDate) {
        PersianCalendar persian = toPersianCalendar(gregorianDate);
        return persian.getDayOfYear();
    }

    /**
     * دریافت شماره روز در سال شمسی از تاریخ شمسی
     *
     * @param persianYear  سال شمسی
     * @param persianMonth ماه شمسی (۱ تا ۱۲)
     * @param persianDay   روز شمسی (۱ تا ۳۱)
     * @return شماره روز از ۱ تا ۳۶۶
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static int getDayOfYear(int persianYear, int persianMonth, int persianDay) {
        PersianCalendar persian = PersianCalendar.of(persianYear, persianMonth, persianDay);
        return persian.getDayOfYear();
    }

    // ==================== متدهای تقویم شمسی ====================

    /**
     * بررسی سال کبیسه بودن در تقویم شمسی
     *
     * <p>در تقویم شمسی، سال کبیسه به این صورت مشخص می‌شود که
     * سالهای ۱، ۵، ۹، ۱۳، ۱۷، ۲۲، ۲۶ و ۳۰ در هر دوره ۳۳ ساله، کبیسه هستند.</p>
     *
     * @param persianYear سال شمسی
     * @return true اگر سال کبیسه باشد
     * @since 1.0.0
     */
    public static boolean isPersianLeapYear(int persianYear) {
        PersianCalendar calendar = PersianCalendar.of(persianYear, 1, 1);
        return calendar.isLeapYear();
    }

    /**
     * دریافت تعداد روزهای یک ماه شمسی خاص
     *
     * <p>ماه‌های ۱ تا ۶ = ۳۱ روز</p>
     * <p>ماه‌های ۷ تا ۱۱ = ۳۰ روز</p>
     * <p>ماه ۱۲ (اسفند) = ۲۹ روز در سال عادی و ۳۰ روز در سال کبیسه</p>
     *
     * @param persianYear  سال شمسی
     * @param persianMonth ماه شمسی (۱ تا ۱۲)
     * @return تعداد روزهای ماه
     * @throws IllegalArgumentException اگر ماه خارج از بازه ۱ تا ۱۲ باشد
     * @since 1.0.0
     */
    public static int getPersianMonthDays(int persianYear, int persianMonth) {
        if (persianMonth < 1 || persianMonth > 12) {
            throw new IllegalArgumentException("ماه باید بین ۱ تا ۱۲ باشد");
        }

        if (persianMonth <= 6) {
            return 31;
        } else if (persianMonth <= 11) {
            return 30;
        } else {
            return isPersianLeapYear(persianYear) ? 30 : 29;
        }
    }

    /**
     * دریافت تعداد روزهای یک سال شمسی
     *
     * @param persianYear سال شمسی
     * @return تعداد روزهای سال (۳۶۵ یا ۳۶۶)
     * @since 1.0.0
     */
    public static int getPersianYearDays(int persianYear) {
        return isPersianLeapYear(persianYear) ? 366 : 365;
    }

    // ==================== متدهای کمکی برای MapStruct ====================

    /**
     * متد کمکی برای استفاده در MapStruct تبدیل LocalDate به String با فرمت پیش‌فرض
     *
     * @param gregorianDate تاریخ میلادی
     * @return تاریخ شمسی به صورت String
     * @since 1.0.0
     */
    public static String toPersianString(LocalDate gregorianDate) {
        return toPersian(gregorianDate);
    }

    /**
     * متد کمکی برای استفاده در MapStruct تبدیل Date به String با فرمت پیش‌فرض
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی به صورت String
     * @since 1.0.0
     */
    public static String toPersianString(Date date) {
        if (date == null) {
            return null;
        }
        return toPersian(date.toInstant().atZone(DEFAULT_ZONE).toLocalDate());
    }

    // ==================== پشتیبانی از انواع تاریخ ====================

    /**
     * تبدیل LocalDateTime میلادی به تاریخ شمسی با فرمت پیش‌فرض
     *
     * @param dateTime تاریخ میلادی با زمان
     * @return تاریخ شمسی به صورت String
     * @since 1.0.0
     */
    public static String toPersian(LocalDateTime dateTime) {
        return toPersian(dateTime.toLocalDate());
    }

    /**
     * تبدیل LocalDateTime میلادی به تاریخ شمسی با فرمت دلخواه
     *
     * @param dateTime تاریخ میلادی با زمان
     * @param pattern  الگوی فرمت
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String toPersian(LocalDateTime dateTime, String pattern) {
        return toPersian(dateTime.toLocalDate(), pattern);
    }

    /**
     * تبدیل ZonedDateTime میلادی به تاریخ شمسی با فرمت پیش‌فرض
     *
     * @param zonedDateTime تاریخ میلادی با منطقه زمانی
     * @return تاریخ شمسی به صورت String
     * @since 1.0.0
     */
    public static String toPersian(ZonedDateTime zonedDateTime) {
        return toPersian(zonedDateTime.toLocalDate());
    }

    /**
     * تبدیل ZonedDateTime میلادی به تاریخ شمسی با فرمت دلخواه
     *
     * @param zonedDateTime تاریخ میلادی با منطقه زمانی
     * @param pattern       الگوی فرمت
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String toPersian(ZonedDateTime zonedDateTime, String pattern) {
        return toPersian(zonedDateTime.toLocalDate(), pattern);
    }

    /**
     * تبدیل java.util.Date میلادی به تاریخ شمسی با فرمت پیش‌فرض
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی به صورت String
     * @since 1.0.0
     */
    public static String toPersian(Date date) {
        if (date == null) {
            return null;
        }
        return toPersian(date.toInstant().atZone(DEFAULT_ZONE).toLocalDate());
    }

    /**
     * تبدیل java.util.Date میلادی به تاریخ شمسی با فرمت دلخواه
     *
     * @param date    تاریخ میلادی
     * @param pattern الگوی فرمت
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String toPersian(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return toPersian(date.toInstant().atZone(DEFAULT_ZONE).toLocalDate(), pattern);
    }

    // ==================== متدهای پیشرفته ====================

    /**
     * دریافت شیء PersianCalendar برای محاسبات بیشتر
     *
     * @param gregorianDate تاریخ میلادی
     * @return شیء PersianCalendar معادل تاریخ شمسی
     * @since 1.0.0
     */
    public static PersianCalendar getPersianCalendar(LocalDate gregorianDate) {
        return toPersianCalendar(gregorianDate);
    }

    /**
     * دریافت اطلاعات کامل تاریخ شمسی از تاریخ میلادی
     *
     * @param gregorianDate تاریخ میلادی
     * @return شیء PersianDateInfo شامل تمام اطلاعات تاریخ شمسی
     * @since 1.0.0
     */
    public static PersianDateInfo getPersianDateInfo(LocalDate gregorianDate) {
        PersianCalendar persian = toPersianCalendar(gregorianDate);
        return new PersianDateInfo(
            persian.getYear(),
            persian.getMonth().getValue(),
            persian.getDayOfMonth(),
            getDayOfWeek(gregorianDate),
            getMonthName(gregorianDate),
            persian.getDayOfYear()
        );
    }

    /**
     * دریافت اطلاعات کامل تاریخ شمسی از تاریخ شمسی
     *
     * @param persianYear  سال شمسی
     * @param persianMonth ماه شمسی (۱ تا ۱۲)
     * @param persianDay   روز شمسی (۱ تا ۳۱)
     * @return شیء PersianDateInfo شامل تمام اطلاعات تاریخ شمسی
     * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
     * @since 1.0.0
     */
    public static PersianDateInfo getPersianDateInfo(int persianYear, int persianMonth,
        int persianDay) {
        PersianCalendar persian = PersianCalendar.of(persianYear, persianMonth, persianDay);
        LocalDate gregorian = toGregorian(persianYear, persianMonth, persianDay);
        return new PersianDateInfo(
            persian.getYear(),
            persian.getMonth().getValue(),
            persian.getDayOfMonth(),
            getDayOfWeek(persianYear, persianMonth, persianDay),
            getMonthName(persianMonth),
            persian.getDayOfYear()
        );
    }

    // ==================== کلاس PersianDateInfo ====================

    /**
     * Persian Date Information
     * <p>
     * کلاس کمکی برای نگهداری اطلاعات کامل تاریخ شمسی شامل سال، ماه، روز، نام روز هفته، نام ماه و
     * روز سال
     *
     * <p>این کلاس به صورت Immutable طراحی شده و برای استفاده در
     * Stream API، Collections و Recordها مناسب است.</p>
     *
     * <p>مثال استفاده:</p>
     * <pre>
     * PersianDateInfo info = PersianDateConverter.getPersianDateInfo(date);
     * System.out.println(info.getYear());        // 1405
     * System.out.println(info.getMonthName());   // تیر
     * System.out.println(info.getDayOfWeek());   // چهارشنبه
     * System.out.println(info.toFullString());   // چهارشنبه 3 تیر 1405
     * </pre>
     *
     * @author Sepidan Team
     * @version 1.0.0
     * @category DateConverter
     * @package net.sepidan.converter
     * @copyright Copyright (c) 2026 Sepidan (info@sepidan.net)
     * @license MIT License
     * @see net.sepidan.converter.PersianDateConverter
     * @since 1.0.0
     */
    public static final class PersianDateInfo {

        /**
         * سال شمسی
         */
        private final int year;

        /**
         * شماره ماه (۱ تا ۱۲)
         */
        private final int month;

        /**
         * شماره روز (۱ تا ۳۱)
         */
        private final int day;

        /**
         * نام روز هفته به فارسی
         */
        private final String dayOfWeek;

        /**
         * نام ماه به فارسی
         */
        private final String monthName;

        /**
         * شماره روز در سال (۱ تا ۳۶۶)
         */
        private final int dayOfYear;

        /**
         * سازنده کلاس PersianDateInfo
         *
         * @param year      سال شمسی
         * @param month     شماره ماه (۱ تا ۱۲)
         * @param day       شماره روز (۱ تا ۳۱)
         * @param dayOfWeek نام روز هفته به فارسی
         * @param monthName نام ماه به فارسی
         * @param dayOfYear شماره روز در سال (۱ تا ۳۶۶)
         */
        public PersianDateInfo(int year, int month, int day, String dayOfWeek,
            String monthName, int dayOfYear) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.dayOfWeek = dayOfWeek;
            this.monthName = monthName;
            this.dayOfYear = dayOfYear;
        }

        /**
         * دریافت سال شمسی
         *
         * @return سال شمسی
         */
        public int getYear() {
            return year;
        }

        /**
         * دریافت شماره ماه
         *
         * @return شماره ماه (۱ تا ۱۲)
         */
        public int getMonth() {
            return month;
        }

        /**
         * دریافت شماره روز
         *
         * @return شماره روز (۱ تا ۳۱)
         */
        public int getDay() {
            return day;
        }

        /**
         * دریافت نام روز هفته به فارسی
         *
         * @return نام روز هفته
         */
        public String getDayOfWeek() {
            return dayOfWeek;
        }

        /**
         * دریافت نام ماه به فارسی
         *
         * @return نام ماه
         */
        public String getMonthName() {
            return monthName;
        }

        /**
         * دریافت شماره روز در سال
         *
         * @return شماره روز (۱ تا ۳۶۶)
         */
        public int getDayOfYear() {
            return dayOfYear;
        }

        /**
         * نمایش تاریخ به فرمت استاندارد با اسلش
         *
         * @return رشته به صورت yyyy/MM/dd
         */
        public String toStandardString() {
            return String.format("%d/%02d/%02d", year, month, day);
        }

        /**
         * نمایش تاریخ به فرمت کامل با نام روز و ماه
         *
         * @return رشته به صورت "روز هفته روز ماه سال"
         */
        public String toFullString() {
            return String.format("%s %d %s %d", dayOfWeek, day, monthName, year);
        }

        /**
         * نمایش تاریخ به فرمت با خط تیره
         *
         * @return رشته به صورت yyyy-MM-dd
         * @since 1.0.0
         */
        public String toDashString() {
            return String.format("%d-%02d-%02d", year, month, day);
        }

        /**
         * نمایش تاریخ با اعداد فارسی
         *
         * @return رشته با اعداد فارسی به صورت yyyy/MM/dd
         * @since 1.0.0
         */
        public String toPersianNumbersString() {
            return convertToPersianNumbers(toStandardString());
        }

        /**
         * تبدیل به LocalDate میلادی
         *
         * @return LocalDate معادل تاریخ میلادی
         * @since 1.0.0
         */
        public LocalDate toGregorian() {
            return PersianDateConverter.toGregorian(year, month, day);
        }

        /**
         * بررسی اعتبار تاریخ
         *
         * @return true اگر تاریخ معتبر باشد
         * @since 1.0.0
         */
        public boolean isValid() {
            return PersianDateConverter.isValidPersianDate(year, month, day);
        }

        /**
         * بررسی سال کبیسه بودن
         *
         * @return true اگر سال کبیسه باشد
         * @since 1.0.0
         */
        public boolean isLeapYear() {
            return PersianDateConverter.isPersianLeapYear(year);
        }

        /**
         * دریافت تعداد روزهای ماه
         *
         * @return تعداد روزهای ماه
         * @since 1.0.0
         */
        public int getMonthDays() {
            return PersianDateConverter.getPersianMonthDays(year, month);
        }

        @Override
        public String toString() {
            return toStandardString();
        }
    }

    // ==================== متدهای کمکی داخلی ====================

    /**
     * تبدیل اعداد انگلیسی به فارسی
     *
     * @param text متنی که شامل اعداد انگلیسی است
     * @return متن با اعداد فارسی
     * @since 1.0.0
     */
    private static String convertToPersianNumbers(String text) {
        if (text == null) {
            return null;
        }
        String[] persianNumbers = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= '0' && c <= '9') {
                result.append(persianNumbers[c - '0']);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}