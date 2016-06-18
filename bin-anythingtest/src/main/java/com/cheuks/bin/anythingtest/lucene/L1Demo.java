package com.cheuks.bin.anythingtest.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class L1Demo {

	private String indexSearch = "D:/javaProject/Bin/bin-anythingtest/indexSearch";
	private String indexDocument = "D:/javaProject/Bin/bin-anythingtest/indexDocument";

	public void createSearch() throws IOException {

		// Document doc = new Document();
		// doc.add();

		Directory directory = FSDirectory.open(Paths.get(indexSearch));

		IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());

		IndexWriter writer = new IndexWriter(directory, conf);
		// // 文档
		// Document doc = null;
		//
		// doc = new Document();
		// doc.add(new StringField("path", indexDocument, Store.YES));
		//
		// // Files.newInputStream(pa, options)
		// doc.add(new TextField("content", Files.newBufferedReader(Paths.get(indexDocument + "/1.txt"), Charset.forName("utf-8"))));
		// writer.addDocument(doc);
		// 添加文件索引
		writer.deleteAll();
		docs(writer, Paths.get(indexDocument));
		// 完成提交
		writer.commit();
		writer.close();
	}

	public void docs(final IndexWriter indexWriter, final Path path) throws IOException {
		if (Files.isDirectory(path)) {
			Files.walkFileTree(path, new FileVisitor<Path>() {

				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					doc(indexWriter, file);
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					System.out.println(file.toFile().getName());
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} else
			doc(indexWriter, path);
	}

	public void doc(final IndexWriter indexWriter, final Path path) throws IOException {
		// 文档
		Document doc = null;

		doc = new Document();
		doc.add(new StringField("path", path.toString(), Store.YES));
		// doc.add(new TextField("content", Files.newBufferedReader(path)));
		String line = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader br = Files.newBufferedReader(path);
		while (null != (line = br.readLine()))
			sb.append(line).append("\n");
		doc.add(new TextField("content", sb.toString(), Store.YES));
		indexWriter.addDocument(doc);
	}

	public void scarch(String str) throws IOException, ParseException, InvalidTokenOffsetsException {

		// IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Paths.get(indexSearch))));
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexSearch)));
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser("content", new StandardAnalyzer());
		Query query = parser.parse(str);
		// 条目
		TopDocs topDocs = indexSearcher.search(query, 10000);
		// 高亮
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red\">", "</span>");
		Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));

		int n = 0;
		String content;
		for (ScoreDoc doc : topDocs.scoreDocs) {
			n = doc.doc;
			Document document = indexSearcher.doc(n);
			content = document.get("content");
			System.err.println(document.get("path"));
			highlighter.setTextFragmenter(new SimpleFragmenter(content.length()));
			// 高亮
			System.err.println(highlighter.getBestFragment(new SimpleAnalyzer(), "content", content));

			// System.err.println(document.get("content"));
		}
	}

	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		new L1Demo().createSearch();
		new L1Demo().scarch("import");
	}

}
