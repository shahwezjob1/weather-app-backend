package com.weather.backend.service;

import com.weather.backend.domain.Advice;
import com.weather.backend.domain.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicAdviceEvaluatorService implements AdviceEvaluatorService {

    private final DynamicAdviceFetcherService dynamicAdviceFetcherService;

    /**
     * Evaluates rules against the given weather object.
     * Returns the advice for the first matching rule or weather description if no rule matches.
     *
     * @param weather Weather object to evaluate
     * @return advice based on the rules
     */

    @Override
    public String getAdvice(Weather weather) {
        List<Advice> advices = dynamicAdviceFetcherService.getRules();

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(weather);

        for (Advice advice : advices) {
            try {
                Expression expression = parser.parseExpression(advice.getCondition());
                Boolean result = expression.getValue(context, Boolean.class);

                if (Boolean.TRUE.equals(result)) {
                    return advice.getValue();
                }
            } catch (Exception e) {
                log.error("Error evaluating rule: " + advice.getCondition() + " - " + e.getMessage());
            }
        }
        return weather.getDescription();
    }
}
