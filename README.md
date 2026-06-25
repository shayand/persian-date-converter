# Persian Date Converter 📅

کتابخانه‌ی تبدیل تاریخ میلادی به شمسی با استفاده از **Time4J**، با پشتیبانی از قابلیت‌های مدرن جاوا (Java 17+)، کمترین تولید Garbage و امکان استفاده در **MapStruct** و **Record**ها.

---

## ✨ ویژگی‌ها

- ✅ تبدیل تاریخ میلادی به شمسی با فرمت‌های مختلف
- ✅ دریافت نام روز هفته و ماه به فارسی
- ✅ پشتیبانی از `LocalDate`، `LocalDateTime`، `ZonedDateTime` و `Date`
- ✅ کمترین تولید Garbage (با استفاده از `Time4J`)
- ✅ قابل استفاده در `MapStruct` به عنوان `@Mapper(uses = PersianDateConverter.class)`
- ✅ پشتیبانی از `Record`ها
- ✅ فرمت‌های متنوع: استاندارد، با خط تیره، با نام ماه، با نام روز هفته
- ✅ پشتیبانی از اعداد فارسی
- ✅ اعتبارسنجی تاریخ شمسی
- ✅ تشخیص سال کبیسه و تعداد روزهای ماه شمسی
- ✅ دریافت اجزای تاریخ (سال، ماه، روز) به صورت جداگانه

---

## 📦 نصب و راه‌اندازی

### افزودن به `pom.xml`:

```xml
<dependency>
    <groupId>net.sepidan</groupId>
    <artifactId>persian-date-converter</artifactId>
    <version>1.0.1</version>
</dependency>
```

### یا در صورت استفاده از build.gradle:

```gradle
implementation 'net.sepidan:persian-date-converter:1.0.1'
```

## 🚀 راهنمای استفاده

### ۱. تبدیل تاریخ میلادی به شمسی

```java
import net.sepidan.persiandate.converter.PersianDateConverter;
import java.time.LocalDate;

public class Example {

    public static void main(String[] args) {
        LocalDate gregorianDate = LocalDate.of(2026, 6, 24);

        // تبدیل با فرمت پیش‌فرض (yyyy/MM/dd)
        String persianDate = PersianDateConverter.toPersian(gregorianDate);
        System.out.println(persianDate); // خروجی: ۱۴۰۲/۰۴/۰۳

        // تبدیل با فرمت دلخواه
        String customFormat = PersianDateConverter.toPersian(gregorianDate, "dd MMMM yyyy");
        System.out.println(customFormat); // خروجی: ۰۳ تیر ۱۴۰۲
    }
}
```

### ۲. دریافت نام روز هفته و ماه

```java
LocalDate date = LocalDate.of(2026, 6, 24);

// دریافت نام روز هفته به فارسی
String dayOfWeek = PersianDateConverter.getDayOfWeek(date);
System.out.println(dayOfWeek); // خروجی: سه‌شنبه

// دریافت نام ماه به فارسی
String monthName = PersianDateConverter.getMonthName(date);
System.out.println(monthName); // خروجی: تیر
```

### ۳. فرمت‌های مختلف با کلاس PersianDateFormatter

```java
import net.sepidan.persiandate.format.PersianDateFormatter;
import net.sepidan.persiandate.format.PersianDateFormatter.PersianDateParts;
import java.time.LocalDate;

LocalDate date = LocalDate.of(2026, 6, 24);

// فرمت استاندارد با اسلش
System.out.

println(PersianDateFormatter.formatStandard(date));
// خروجی: ۱۴۰۲/۰۴/۰۳

// فرمت با خط تیره
    System.out.

println(PersianDateFormatter.formatWithDash(date));
// خروجی: ۱۴۰۲-۰۴-۰۳

// فرمت با نام ماه کامل
    System.out.

println(PersianDateFormatter.formatWithMonthName(date));
// خروجی: ۰۳ تیر ۱۴۰۲

// فرمت با نام روز هفته
    System.out.

println(PersianDateFormatter.formatWithWeekday(date));
// خروجی: سه‌شنبه ۰۳ تیر ۱۴۰۲

// فرمت با اعداد فارسی
    System.out.

println(PersianDateFormatter.formatWithPersianNumbers(date));
// خروجی: ۱۴۰۲/۰۴/۰۳

// دریافت اجزای تاریخ به صورت Record
PersianDateParts parts = PersianDateFormatter.getDateParts(date);
System.out.

println(parts.year());   // ۱۴۰۲
    System.out.

println(parts.month());  // ۴
    System.out.

println(parts.day());    // ۳
```

### ۴. استفاده در MapStruct

```java
import net.sepidan.persiandate.converter.PersianDateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PersianDateConverter.class)
public interface UserMapper {

    @Mapping(target = "persianBirthDate", source = "birthDate", qualifiedByName = "toPersianString")
    UserDto toDto(UserEntity entity);

    // یا استفاده مستقیم از متدها
    default String mapLocalDateToString(LocalDate date) {
        return PersianDateConverter.toPersianString(date);
    }
}
```

### ۵. استفاده با Recordها

```java
public record UserRecord(
    String firstName,
    String lastName,
    LocalDate birthDate
) {
    // متد کمکی برای دریافت تاریخ شمسی
    public String getPersianBirthDate() {
        return PersianDateConverter.toPersian(birthDate);
    }

    public String getPersianBirthDate(String pattern) {
        return PersianDateConverter.toPersian(birthDate, pattern);
    }
}

// استفاده:
UserRecord user = new UserRecord("شایان", "داورزنی", LocalDate.of(1990, 12, 26));
System.out.println(user.getPersianBirthDate());
// خروجی: ۱۳۶۹/۱۰/۰۵
```

### ۶. بررسی سال کبیسه و تعداد روزهای ماه

```java
// بررسی سال کبیسه شمسی
boolean isLeap = PersianDateConverter.isPersianLeapYear(1402);
System.out.println(isLeap); // false

// دریافت تعداد روزهای ماه شمسی
int days = PersianDateConverter.getPersianMonthDays(1402, 6); // ماه شهریور
System.out.println(days); // ۳۱
```

### ۷. تاریخ امروز

```java
import net.sepidan.persiandate.util.PersianDateUtils;

String today = PersianDateUtils.today();
System.out.

println(today); // خروجی: ۱۴۰۲/۰۴/۰۳

String todayWithMonth = PersianDateUtils.today("dd MMMM yyyy");
System.out.

println(todayWithMonth); // خروجی: ۰۳ تیر ۱۴۰۲
```

### ۸. کار با java.util.Date

```java
import java.util.Date;

Date date = new Date();
String persianDate = PersianDateConverter.toPersian(date);
System.out.println(persianDate);
```

### ۹. کار با LocalDateTime و ZonedDateTime

```java
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;

LocalDateTime dateTime = LocalDateTime.now();
String persianDateTime = PersianDateConverter.toPersian(dateTime, "yyyy/MM/dd HH:mm:ss");
System.out.println(persianDateTime);

ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Tehran"));
String persianZoned = PersianDateConverter.toPersian(zonedDateTime);
```

### ۱۰. دریافت شماره روز و هفته در سال شمسی

```java
LocalDate date = LocalDate.of(2026, 6, 24);

int dayOfYear = PersianDateConverter.getDayOfYear(date);
System.out.println("روز " + dayOfYear + " از سال"); // روز ۹۵ از سال

```

### ۱۱. دریافت تاریخ میلادی از تاریخ شمسی

```java
LocalDate start = PersianDateConverter.toGregorian("1405-04-01");

int dayOfYear = PersianDateConverter.getDayOfYear(date);
System.out.println("روز " + dayOfYear + " از سال"); // روز ۹۵ از سال

```

---
### 📋 فرمت‌های پشتیبانی شده

| فرمت                  | توضیح                      | مثال                |
| --------------------- | -------------------------- | ------------------- |
| `yyyy/MM/dd`          | استاندارد با اسلش          | ۱۴۰۲/۰۴/۰۳          |
| `yyyy-MM-dd`          | با خط تیره                 | ۱۴۰۲-۰۴-۰۳          |
| `dd MMMM yyyy`        | با نام کامل ماه            | ۰۳ تیر ۱۴۰۲         |
| `dd MMM yyyy`         | با نام مخفف ماه            | ۰۳ تیر ۱۴۰۲         |
| `EEEE d MMMM yyyy`    | با نام روز هفته و ماه کامل | سه‌شنبه ۰۳ تیر ۱۴۰۲ |
| `yyyy/MM/dd HH:mm:ss` | با زمان                    | ۱۴۰۲/۰۴/۰۳ ۱۴:۳۰:۲۵ |
| `yyyy-MM-dd HH:mm:ss` | با زمان و خط تیره          | ۱۴۰۲-۰۴-۰۳ ۱۴:۳۰:۲۵ |
| `d MMMM yyyy`         | فقط روز و ماه کامل         | ۳ تیر ۱۴۰۲          |
| `EEEE`                | فقط نام روز هفته           | سه‌شنبه             |
| `MMMM`                | فقط نام ماه                | تیر                 |
| `yyyy`                | فقط سال                    | ۱۴۰۲                |
| `MM`                  | فقط ماه (عدد)              | ۰۴                  |
| `dd`                  | فقط روز (عدد)              | ۰۳                  |

### 📌 راهنمای الگوهای فرمت

| الگو   | معنی              | مثال       |
| ------ | ----------------- | ---------- |
| `yyyy` | سال ۴ رقمی        | ۱۴۰۲       |
| `yy`   | سال ۲ رقمی        | ۰۲         |
| `MM`   | ماه ۲ رقمی        | ۰۴         |
| `M`    | ماه ۱ یا ۲ رقمی   | ۴          |
| `dd`   | روز ۲ رقمی        | ۰۳         |
| `d`    | روز ۱ یا ۲ رقمی   | ۳          |
| `MMMM` | نام کامل ماه      | تیر        |
| `MMM`  | نام مخفف ماه      | تیر        |
| `EEEE` | نام کامل روز هفته | سه‌شنبه    |
| `EEE`  | نام مخفف روز هفته | سه‌        |
| `HH`   | ساعت (۲۴ ساعته)   | ۱۴         |
| `hh`   | ساعت (۱۲ ساعته)   | ۰۲         |
| `mm`   | دقیقه             | ۳۰         |
| `ss`   | ثانیه             | ۲۵         |
| `a`    | قبل/بعد از ظهر    | بعد از ظهر |

**نکته:** برای فرمت‌های بیشتر، از الگوهای استاندارد `Time4J` استفاده کنید.

### 💡 مثال‌های ترکیبی

```java
// فرمت دلخواه با ترکیب الگوها
String result1 = PersianDateConverter.toPersian(date, "EEEE d MMMM yyyy");
// خروجی: سه‌شنبه ۳ تیر ۱۴۰۲
String result2 = PersianDateConverter.toPersian(date, "yyyy/MM/dd - HH:mm");
// خروجی: ۱۴۰۲/۰۴/۰۳ - ۱۴:۳۰
String result3 = PersianDateConverter.toPersian(date, "dd MMM yyyy");
// خروجی: ۰۳ تیر ۱۴۰۲
String result4 = PersianDateConverter.toPersian(date, "EEEE, d MMMM");
// خروجی: سه‌شنبه, ۳ تیر
```

### 🛠️ فرمت‌های آماده در کلاس `PersianDateFormatter`

```java
// استفاده از فرمت‌های از پیش تعیین شده
PersianDateFormatter.formatStandard(date);        // ۱۴۰۲/۰۴/۰۳
PersianDateFormatter.formatWithDash(date);        // ۱۴۰۲-۰۴-۰۳
PersianDateFormatter.formatWithMonthName(date);   // ۰۳ تیر ۱۴۰۲
PersianDateFormatter.formatWithWeekday(date);     // سه‌شنبه ۰۳ تیر ۱۴۰۲
PersianDateFormatter.formatWithPersianNumbers(date); // ۱۴۰۲/۰۴/۰۳ (با اعداد فارسی)
```

### 🧪 تست‌ها

برای اجرای تست‌ها:

```bash
mvn test
```

مثال تست:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersianDateConverterTest {

    @Test
    void testConvertToPersian() {
        LocalDate date = LocalDate.of(2026, 6, 24);
        String result = PersianDateConverter.toPersian(date);
        assertEquals("۱۴۰۲/۰۴/۰۳", result);
    }

    @Test
    void testGetDayOfWeek() {
        LocalDate date = LocalDate.of(2026, 6, 24);
        String result = PersianDateConverter.getDayOfWeek(date);
        assertEquals("سه‌شنبه", result);
    }
}
```

#### متودهای اضافی
سایر متودها و استفاده ها در ۲۳ تست کیس موجود در این کلاس قابل دستیابی است
---

## ⚡ نکات عملکردی

کمترین تولید Garbage: استفاده از Time4J بهینه‌سازی شده برای کاهش تولید اشیاء زائد

Thread-Safe: تمام متدها static و بدون state هستند

Fast: تبدیل‌ها در کسری از میلی‌ثانیه انجام می‌شوند

---

## 🔧 وابستگی‌ها

Time4J - کتابخانه اصلی برای تبدیل تقویم

Java 17 یا بالاتر

## 📄 لایسنس

این پروژه تحت لایسنس MIT منتشر شده است.

## 🤝 مشارکت

برای مشارکت در بهبود این کتابخانه، لطفاً یک Pull Request ارسال کنید یا Issue جدید باز کنید.

## 📞 ارتباط با ما

وب‌سایت: sepidan.net

ایمیل: info@sepidan.net

ایمیل: shayandavarzani@gmail.com

## تاریخ آخرین به‌روزرسانی: ۲۴ ژوئن ۲۰۲۶ یا ۳ تیر ۱۴۰۵

## ⭐ حمایت

اگر از این کتابخانه خوشتان آمد، به آن Star بدهید! 🌟
