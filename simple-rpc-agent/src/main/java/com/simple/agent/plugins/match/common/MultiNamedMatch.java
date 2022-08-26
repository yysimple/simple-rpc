package com.simple.agent.plugins.match.common;

import com.simple.agent.plugins.match.AbstractMatch;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-08-26 14:18
 **/
public class MultiNamedMatch extends AbstractMatch {

    private final List<String> matchClassNames;

    private MultiNamedMatch(String[] classNames) {
        if (classNames == null || classNames.length == 0) {
            throw new IllegalArgumentException("match class names is null");
        }
        this.matchClassNames = Arrays.asList(classNames);
    }

    @Override
    public ElementMatcher.Junction containJunction() {
        ElementMatcher.Junction junction = null;
        for (String name : matchClassNames) {
            if (junction == null) {
                junction = named(name);
            } else {
                junction = junction.or(named(name));
            }
        }
        return junction;
    }

    @Override
    public ElementMatcher.Junction ignoreJunction() {
        ElementMatcher.Junction junction = null;
        for (String name : matchClassNames) {
            if (junction == null) {
                junction = named(name);
            } else {
                junction = junction.and(named(name));
            }
        }
        return junction;
    }

    public static MultiNamedMatch byMultiClassMatch(String... classNames) {
        return new MultiNamedMatch(classNames);
    }

}
