package com.cheuks.bin.anythingtest.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class XaAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new WhitespaceTokenizer());
	}

	public static void main(String[] args) throws IOException {
		XaAnalyzer analyzer = new XaAnalyzer();
		// TokenStream stream = analyzer.tokenStream("field", new StringReader(text));
		TokenStream stream = analyzer.tokenStream("xx", "你好吗？ 我不好  你小心 点");

		// get the CharTermAttribute from the TokenStream
		CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);

		try {
			stream.reset();

			// print all tokens until stream is exhausted
			while (stream.incrementToken()) {
				System.out.println(termAtt.toString());
			}
			stream.end();
		} finally {
			stream.close();
		}
	}

}
