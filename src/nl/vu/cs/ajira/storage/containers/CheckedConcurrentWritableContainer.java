package nl.vu.cs.ajira.storage.containers;

import nl.vu.cs.ajira.storage.Writable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// A ConcurrentWritableContainer that throws an error when an addition does not fit.
public class CheckedConcurrentWritableContainer<K extends Writable> extends
	ConcurrentWritableContainer<K> {

    static final Logger log = LoggerFactory
	    .getLogger(ConcurrentWritableContainer.class);

    public CheckedConcurrentWritableContainer(int size) {
    	super(size);
    }

    @Override
    public boolean add(K element) {
    	if (!super.add(element)) {
            throw new Error("The container is too small for this addition!");
    	}
    	return true;
    }

    @Override
    public boolean addAll(WritableContainer<K> buffer) {
    	if (!super.addAll(buffer)) {
            throw new Error("The container is too small for this addition!");
    	}
    	return true;
    }
}
