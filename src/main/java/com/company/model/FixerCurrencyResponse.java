package com.company.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter

public class FixerCurrencyResponse {
    @JsonProperty("base")
    String base;
    @JsonProperty("date")
    Date date;
    @JsonProperty("rates")
    Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public Date getDate() {
        return date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public Format getFormatter() {
        return formatter;
    }

    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public String toString() {
        return "{" +
                "base='" + base + '\'' +
                ", date=" + formatter.format(date) +
                ", rates=" + rates +
                '}';
    }
}
