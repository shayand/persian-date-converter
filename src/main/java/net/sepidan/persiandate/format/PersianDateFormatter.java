package net.sepidan.persiandate.format;

import net.sepidan.persiandate.converter.PersianDateConverter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Persian Date Formatter
 * <p>
 * کلاس ابزار برای فرمت‌های مختلف تاریخ شمسی
 * شامل فرمت‌های استاندارد، با نام ماه، با نام روز هفته و اعداد فارسی
 * </p>
 *
 * <p>ویژگی‌ها:</p>
 * <ul>
 *   <li>فرمت استاندارد با اسلش: ۱۴۰۵/۰۴/۰۳</li>
 *   <li>فرمت با خط تیره: ۱۴۰۵-۰۴-۰۳</li>
 *   <li>فرمت با نام ماه کامل: ۰۳ تیر ۱۴۰۵</li>
 *   <li>فرمت با نام روز هفته: چهارشنبه ۰۳ تیر ۱۴۰۵</li>
 *   <li>فرمت با اعداد فارسی: ۱۴۰۵/۰۴/۰۳</li>
 *   <li>فرمت‌های ترکیبی و دلخواه</li>
 *   <li>دریافت اجزای تاریخ به صورت Record</li>
 * </ul>
 *
 * <p>مثال استفاده:</p>
 * <pre>
 * LocalDate date = LocalDate.of(2026, 6, 24);
 *
 * // فرمت استاندارد
 * String standard = PersianDateFormatter.formatStandard(date);
 * // خروجی: ۱۴۰۵/۰۴/۰۳
 *
 * // فرمت با نام ماه
 * String monthName = PersianDateFormatter.formatWithMonthName(date);
 * // خروجی: ۰۳ تیر ۱۴۰۵
 *
 * // فرمت با اعداد فارسی
 * String persian = PersianDateFormatter.formatWithPersianNumbers(date);
 * // خروجی: ۱۴۰۵/۰۴/۰۳
 * </pre>
 *
 * @author Sepidan Team (Shayan Davarzani [shayandavarzani@gmail.com])
 * @version 1.0.0
 * @see PersianDateConverter
 * @see PersianDateFormatter.PersianDateParts
 * @since 1.0.0
 */
public final class PersianDateFormatter {

    /**
     * فرمت استاندارد با اسلش
     * مثال: ۱۴۰۵/۰۴/۰۳
     */
    public static final String FORMAT_STANDARD = "yyyy/MM/dd";

    /**
     * فرمت با خط تیره
     * مثال: ۱۴۰۵-۰۴-۰۳
     */
    public static final String FORMAT_WITH_DASH = "yyyy-MM-dd";

    /**
     * فرمت با اسلش (همان استاندارد)
     * مثال: ۱۴۰۵/۰۴/۰۳
     */
    public static final String FORMAT_WITH_SLASH = "yyyy/MM/dd";

    /**
     * فرمت با زمان
     * مثال: ۱۴۰۵/۰۴/۰۳ ۱۴:۳۰:۲۵
     */
    public static final String FORMAT_WITH_TIME = "yyyy/MM/dd HH:mm:ss";

    /**
     * فرمت با اعداد فارسی
     * مثال: ۱۴۰۵/۰۴/۰۳
     */
    public static final String FORMAT_PERSIAN_NUMBERS = "yyyy/MM/dd";

    /**
     * فرمت با نام کامل ماه
     * مثال: ۰۳ تیر ۱۴۰۵
     */
    public static final String FORMAT_MONTH_NAME = "dd MMMM yyyy";

    /**
     * فرمت با نام مخفف ماه
     * مثال: ۰۳ تیر ۱۴۰۵
     */
    public static final String FORMAT_SHORT_MONTH = "dd MMM yyyy";

    /**
     * فرمت با نام روز هفته و ماه کامل
     * مثال: چهارشنبه ۰۳ تیر ۱۴۰۵
     */
    public static final String FORMAT_WEEKDAY = "EEEE d MMMM yyyy";

    /**
     * نقشه تبدیل اعداد انگلیسی به فارسی
     */
    private static final Map<String, String> PERSIAN_NUMBERS = new HashMap<>();

    static {
        // جدول تبدیل اعداد انگلیسی به فارسی
        PERSIAN_NUMBERS.put("0", "۰");
        PERSIAN_NUMBERS.put("1", "۱");
        PERSIAN_NUMBERS.put("2", "۲");
        PERSIAN_NUMBERS.put("3", "۳");
        PERSIAN_NUMBERS.put("4", "۴");
        PERSIAN_NUMBERS.put("5", "۵");
        PERSIAN_NUMBERS.put("6", "۶");
        PERSIAN_NUMBERS.put("7", "۷");
        PERSIAN_NUMBERS.put("8", "۸");
        PERSIAN_NUMBERS.put("9", "۹");
    }

    /**
     * سازنده خصوصی برای جلوگیری از نمونه‌سازی
     *
     * @throws UnsupportedOperationException همیشه پرتاب می‌شود
     */
    private PersianDateFormatter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ==================== فرمت‌های از پیش تعیین شده ====================

    /**
     * فرمت استاندارد با اسلش
     * <p>مثال: ۱۴۰۵/۰۴/۰۳</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String formatStandard(LocalDate date) {
        return PersianDateConverter.toPersian(date, FORMAT_STANDARD);
    }

    /**
     * فرمت با خط تیره
     * <p>مثال: ۱۴۰۵-۰۴-۰۳</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String formatWithDash(LocalDate date) {
        return PersianDateConverter.toPersian(date, FORMAT_WITH_DASH);
    }

    /**
     * فرمت با نام ماه کامل
     * <p>مثال: ۰۳ تیر ۱۴۰۵</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String formatWithMonthName(LocalDate date) {
        return PersianDateConverter.toPersian(date, FORMAT_MONTH_NAME);
    }

    /**
     * فرمت با نام روز هفته و ماه کامل
     * <p>مثال: چهارشنبه ۰۳ تیر ۱۴۰۵</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده
     * @since 1.0.0
     */
    public static String formatWithWeekday(LocalDate date) {
        return PersianDateConverter.toPersian(date, FORMAT_WEEKDAY);
    }

    /**
     * فرمت با اعداد فارسی
     * <p>مثال: ۱۴۰۵/۰۴/۰۳</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده با اعداد فارسی
     * @since 1.0.0
     */
    public static String formatWithPersianNumbers(LocalDate date) {
        String result = PersianDateConverter.toPersian(date, FORMAT_STANDARD);
        return convertToPersianNumbers(result);
    }

    /**
     * فرمت با اعداد فارسی و نام ماه
     * <p>مثال: ۰۳ تیر ۱۴۰۵</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده با اعداد فارسی و نام ماه
     * @since 1.0.0
     */
    public static String formatWithPersianNumbersAndMonth(LocalDate date) {
        String result = PersianDateConverter.toPersian(date, FORMAT_MONTH_NAME);
        return convertToPersianNumbers(result);
    }

    /**
     * فرمت با اسلش و اعداد فارسی
     * <p>مثال: ۱۴۰۵/۰۴/۰۳</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده با اعداد فارسی
     * @since 1.0.0
     */
    public static String formatPersianSlash(LocalDate date) {
        return formatWithPersianNumbers(date);
    }

    /**
     * فرمت با خط تیره و اعداد فارسی
     * <p>مثال: ۱۴۰۵-۰۴-۰۳</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده با اعداد فارسی و خط تیره
     * @since 1.0.0
     */
    public static String formatPersianDash(LocalDate date) {
        String result = PersianDateConverter.toPersian(date, FORMAT_WITH_DASH);
        return convertToPersianNumbers(result);
    }

    /**
     * فرمت با نام ماه و اعداد فارسی
     * <p>مثال: ۰۳ تیر ۱۴۰۵</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده با اعداد فارسی و نام ماه
     * @since 1.0.0
     */
    public static String formatPersianMonth(LocalDate date) {
        return formatWithPersianNumbersAndMonth(date);
    }

    /**
     * فرمت کامل با نام روز هفته، نام ماه و اعداد فارسی
     * <p>مثال: چهارشنبه ۰۳ تیر ۱۴۰۵</p>
     *
     * @param date تاریخ میلادی
     * @return تاریخ شمسی فرمت شده کامل با اعداد فارسی
     * @since 1.0.0
     */
    public static String formatPersianFull(LocalDate date) {
        String result = PersianDateConverter.toPersian(date, FORMAT_WEEKDAY);
        return convertToPersianNumbers(result);
    }

    // ==================== فرمت‌های ترکیبی و دلخواه ====================

    /**
     * دریافت تاریخ شمسی با فرمت دلخواه
     *
     * @param date    تاریخ میلادی
     * @param pattern الگوی فرمت (مثلاً yyyy/MM/dd یا dd MMMM yyyy)
     * @return تاریخ شمسی فرمت شده
     * @throws IllegalArgumentException اگر الگوی فرمت نامعتبر باشد
     * @since 1.0.0
     */
    public static String formatCustom(LocalDate date, String pattern) {
        return PersianDateConverter.toPersian(date, pattern);
    }

    /**
     * دریافت تاریخ شمسی با فرمت دلخواه و اعداد فارسی
     *
     * @param date    تاریخ میلادی
     * @param pattern الگوی فرمت (مثلاً yyyy/MM/dd یا dd MMMM yyyy)
     * @return تاریخ شمسی فرمت شده با اعداد فارسی
     * @throws IllegalArgumentException اگر الگوی فرمت نامعتبر باشد
     * @since 1.0.0
     */
    public static String formatCustomWithPersianNumbers(LocalDate date, String pattern) {
        String result = PersianDateConverter.toPersian(date, pattern);
        return convertToPersianNumbers(result);
    }

    // ==================== دریافت اجزای تاریخ ====================

    /**
     * دریافت اجزای تاریخ شمسی به صورت Record
     * <p>مثال:</p>
     * <pre>
     * PersianDateParts parts = PersianDateFormatter.getDateParts(date);
     * System.out.println(parts.year());   // ۱۴۰۵
     * System.out.println(parts.month());  // ۴
     * System.out.println(parts.day());    // ۳
     * </pre>
     *
     * @param date تاریخ میلادی
     * @return شیء PersianDateParts شامل سال، ماه و روز
     * @since 1.0.0
     */
    public static PersianDateParts getDateParts(LocalDate date) {
        String persianDate = PersianDateConverter.toPersian(date, "yyyy/MM/dd");
        String[] parts = persianDate.split("/");
        return new PersianDateParts(
            Integer.parseInt(parts[0]), // سال
            Integer.parseInt(parts[1]), // ماه
            Integer.parseInt(parts[2])  // روز
        );
    }

    /**
     * دریافت اجزای تاریخ شمسی به صورت Record با اعداد فارسی
     *
     * @param date تاریخ میلادی
     * @return شیء PersianDateParts شامل سال، ماه و روز با اعداد فارسی
     * @since 1.0.0
     */
    public static PersianDateParts getDatePartsWithPersianNumbers(LocalDate date) {
        PersianDateParts parts = getDateParts(date);
        return new PersianDateParts(
            Integer.parseInt(convertToPersianNumbers(String.valueOf(parts.year()))),
            Integer.parseInt(convertToPersianNumbers(String.valueOf(parts.month()))),
            Integer.parseInt(convertToPersianNumbers(String.valueOf(parts.day())))
        );
    }

    // ==================== متدهای کمکی ====================

    /**
     * تبدیل اعداد انگلیسی به فارسی
     *
     * @param text متنی که شامل اعداد انگلیسی است
     * @return متن با اعداد فارسی
     * @since 1.0.0
     */
    public static String convertToPersianNumbers(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            String digit = String.valueOf(c);
            result.append(PERSIAN_NUMBERS.getOrDefault(digit, digit));
        }
        return result.toString();
    }

    /**
     * تبدیل اعداد فارسی به انگلیسی
     *
     * @param text متنی که شامل اعداد فارسی است
     * @return متن با اعداد انگلیسی
     * @since 1.0.0
     */
    public static String convertToEnglishNumbers(String text) {
        if (text == null) {
            return null;
        }
        Map<String, String> englishNumbers = new HashMap<>();
        for (Map.Entry<String, String> entry : PERSIAN_NUMBERS.entrySet()) {
            englishNumbers.put(entry.getValue(), entry.getKey());
        }
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            String digit = String.valueOf(c);
            result.append(englishNumbers.getOrDefault(digit, digit));
        }
        return result.toString();
    }

    // ==================== کلاس PersianDateParts (Record) ====================

    /**
     * Persian Date Parts Record
     * <p>
     * کلاس Record برای نگهداری اجزای تاریخ شمسی
     * شامل سال، ماه و روز به صورت اعداد
     * </p>
     * <p>
     * این کلاس به صورت Immutable طراحی شده و برای استفاده در
     * Stream API و Collections مناسب است.
     * </p>
     * <p>مثال استفاده:</p>
     * <pre>
     * PersianDateParts parts = new PersianDateParts(1405, 4, 3);
     * System.out.println(parts.year());   // 1405
     * System.out.println(parts.month());  // 4
     * System.out.println(parts.day());    // 3
     * System.out.println(parts);          // 1405/04/03
     * </pre>
     *
     * @author Sepidan Team
     * @version 1.0.0
     * @see PersianDateFormatter
     * @since 1.0.0
     */
    public record PersianDateParts(int year, int month, int day) {

        /**
         * نمایش تاریخ به فرمت استاندارد با اسلش
         *
         * @return رشته به صورت yyyy/MM/dd
         */
        @Override
        public String toString() {
            return String.format("%d/%02d/%02d", year, month, day);
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
            String str = toString();
            return convertToPersianNumbers(str);
        }

        /**
         * نمایش تاریخ با اعداد فارسی و خط تیره
         *
         * @return رشته با اعداد فارسی به صورت yyyy-MM-dd
         * @since 1.0.0
         */
        public String toPersianNumbersDashString() {
            String str = toDashString();
            return convertToPersianNumbers(str);
        }

        /**
         * تبدیل به LocalDate میلادی
         *
         * @return LocalDate معادل تاریخ میلادی
         * @throws IllegalArgumentException اگر تاریخ نامعتبر باشد
         * @since 1.0.0
         */
        public LocalDate toGregorian() {
            return PersianDateConverter.toGregorian(year, month, day);
        }

        /**
         * بررسی اعتبار تاریخ
         *
         * @return {@code true} اگر تاریخ معتبر باشد
         * @since 1.0.0
         */
        public boolean isValid() {
            return PersianDateConverter.isValidPersianDate(year, month, day);
        }

        /**
         * دریافت نام ماه
         *
         * @return نام ماه به فارسی
         * @since 1.0.0
         */
        public String getMonthName() {
            return PersianDateConverter.getMonthName(toGregorian());
        }

        /**
         * دریافت نام روز هفته
         *
         * @return نام روز هفته به فارسی
         * @since 1.0.0
         */
        public String getDayOfWeek() {
            return PersianDateConverter.getDayOfWeek(toGregorian());
        }

        /**
         * دریافت روز سال
         *
         * @return شماره روز از ۱ تا ۳۶۶
         * @since 1.0.0
         */
        public int getDayOfYear() {
            return PersianDateConverter.getDayOfYear(toGregorian());
        }
    }
}