package com.cheuks.bin.anythingtest.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class MultiReaderTest extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new WhitespaceTokenizer());
	}

	public static void main(String[] args) throws IOException {

		Directory d = FSDirectory.open(Paths.get(""));
		Directory d1 = FSDirectory.open(Paths.get(""));
		Directory d2 = FSDirectory.open(Paths.get(""));
		Directory d3 = FSDirectory.open(Paths.get(""));
		Directory d4 = FSDirectory.open(Paths.get(""));

		MultiReader mr = new MultiReader(DirectoryReader.open(d), DirectoryReader.open(d1), DirectoryReader.open(d2), DirectoryReader.open(d3), DirectoryReader.open(d4));

		IndexSearcher searcher = new IndexSearcher(mr, Executors.newCachedThreadPool());

	}

}
