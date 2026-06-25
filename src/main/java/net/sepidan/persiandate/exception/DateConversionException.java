package net.sepidan.persiandate.exception;

import net.sepidan.persiandate.converter.PersianDateConverter;

/**
 * Date Conversion Exception
 * <p>
 * Exception سفارشی برای خطاهای مربوط به تبدیل تاریخ
 * این کلاس برای مدیریت خطاهای تبدیل تاریخ میلادی به شمسی و برعکس استفاده می‌شود
 * </p>
 *
 * <p>موارد استفاده:</p>
 * <ul>
 *   <li>تاریخ شمسی نامعتبر</li>
 *   <li>فرمت تاریخ نادرست</li>
 *   <li>خطا در تبدیل تقویم</li>
 *   <li>خطا در پردازش تاریخ</li>
 * </ul>
 *
 * <p>مثال استفاده:</p>
 * <pre>
 * try {
 *     LocalDate date = PersianDateConverter.toGregorian(1403, 13, 1);
 * } catch (DateConversionException e) {
 *     System.err.println("خطا در تبدیل تاریخ: " + e.getMessage());
 * }
 * </pre>
 *
 * @author Sepidan Team (Shayan Davarzani [shayandavarzani@gmail.com])
 * @version 1.0.0
 * @see PersianDateConverter
 * @see java.lang.RuntimeException
 * @since 1.0.0
 */
public class DateConversionException extends RuntimeException {

    /**
     * شناسه نسخه برای سریالایزیشن
     */
    private static final long serialVersionUID = 1L;

    /**
     * کد خطا برای شناسایی نوع خطا
     */
    private final String errorCode;

    /**
     * سازنده با پیام خطا
     *
     * @param message پیام خطا
     */
    public DateConversionException(String message) {
        super(message);
        this.errorCode = "DATE_CONVERSION_ERROR";
    }

    /**
     * سازنده با پیام خطا و علت خطا
     *
     * @param message پیام خطا
     * @param cause   علت خطا
     */
    public DateConversionException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "DATE_CONVERSION_ERROR";
    }

    /**
     * سازنده با پیام خطا، علت خطا و کد خطا
     *
     * @param message   پیام خطا
     * @param cause     علت خطا
     * @param errorCode کد خطا
     */
    public DateConversionException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * سازنده با پیام خطا و کد خطا
     *
     * @param message   پیام خطا
     * @param errorCode کد خطا
     */
    public DateConversionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * دریافت کد خطا
     *
     * @return کد خطا
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * دریافت پیام خطا به همراه کد خطا
     *
     * @return پیام کامل خطا
     */
    @Override
    public String getMessage() {
        return String.format("[%s] %s", errorCode, super.getMessage());
    }

    /**
     * دریافت پیام خطا به صورت فرمت شده
     *
     * @return پیام خطا با جزئیات کامل
     */
    public String getFormattedMessage() {
        return String.format("Date Conversion Error [Code: %s]: %s", errorCode, super.getMessage());
    }
}