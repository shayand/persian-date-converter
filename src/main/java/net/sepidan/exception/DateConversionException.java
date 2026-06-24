package net.sepidan.exception;

/**
 * Date Conversion Exception
 *
 * Exception سفارشی برای خطاهای مربوط به تبدیل تاریخ
 *
 * @category   DateConverter
 * @package    net.sepidan.exception
 * @author     Sepidan Team (Shayan Davarzani [shayandavarzani@gmail.com])
 * @copyright  Copyright (c) 2026 Sepidan (info@sepidan.net)
 * @license    MIT License
 * @version    1.0.0
 * @since      1.0.0
 */
public class DateConversionException extends RuntimeException {

    public DateConversionException(String message) {
        super(message);
    }

    public DateConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
