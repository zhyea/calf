package org.chobit.calf.tools;

import com.hankcs.hanlp.HanLP;
import org.chobit.calf.utils.Strings;

import java.util.List;

import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
public final class ContentExtractor {


    public static String extractKeywords(String content, int num) {
        if (isBlank(content)) {
            return "";
        }
        String s = content.replace("<p>", "").replace("</p>", "");
        List<String> list = HanLP.extractKeyword(s, num);
        return Strings.mkString(list);
    }

    public static String extractKeywords2(String content, int num) {
        if (isBlank(content)) {
            return "";
        }
        String s = content.replace("<p>", "").replace("</p>", "");
        List<String> list = HanLP.extractKeyword(s, 50);
        TokenSet set = new TokenSet();
        set.addAll(list);
        return Strings.mkString(set, num);
    }

    public static String extractSummary(String content, int size) {
        if (isBlank(content)) {
            return "";
        }
        String s = content.replace("<p>", "").replace("</p>", "");
        List<String> list = HanLP.extractSummary(s, size);
        return Strings.mkString(list);
    }


    private ContentExtractor() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
