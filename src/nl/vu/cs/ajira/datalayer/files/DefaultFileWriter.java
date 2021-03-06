package nl.vu.cs.ajira.datalayer.files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.vu.cs.ajira.data.types.Tuple;

public class DefaultFileWriter implements FileWriter {

	final static Logger log = LoggerFactory.getLogger(DefaultFileWriter.class);

	protected BufferedOutputStream writer = null;

	@Override
	public void init(File file) throws IOException {
		boolean compressGZip = file.getParent().endsWith(".gz");
		if (compressGZip) {
			writer = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		} else {
			writer = new BufferedOutputStream(new FileOutputStream(file));
		}
	}

	/**
	 * This method writes the specified tuple to the output stream, by first
	 * constructing a string from the tuple (fields of the tuple separated by a
	 * tab) and then writing that string to the output stream.
	 *
	 * @param tuple
	 *            the tuple to write
	 * @throws IOException
	 *             is thrown on write error
	 */
	@Override
	public void write(Tuple tuple) throws IOException {
		if (tuple.getNElements() > 0) {
			StringBuilder b = new StringBuilder(tuple.get(0).toString());
			for (int i = 1; i < tuple.getNElements(); ++i) {
				b.append('\t').append(tuple.get(i).toString());
			}
			b.append('\n');
			writer.write(b.toString().getBytes());
		}
	}

	/**
	 * Closes the output stream.
	 *
	 * @throws IOException
	 *             is thrown when closing the underlying output stream throws
	 *             it.
	 */
	@Override
	public void close() throws IOException {
		writer.close();
	}
}
