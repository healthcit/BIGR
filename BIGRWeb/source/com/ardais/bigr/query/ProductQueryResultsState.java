package com.ardais.bigr.query;

import java.util.List;

/**
 * This class holds all of maintained state about query results, including the 
 * ids browsing through, items that are on hold, removed and selected.
 * 
 * 
 */
public class ProductQueryResultsState {

//    String[][] selectedIds; // array of arrays of ids selected for each chunk
//    String[] onHoldIds; // elements on hold
//    String[] removedIds;// tried to put on hold, but are not available any more

    int currentChunkNumber = 0;
    int currentChunk = 0;
    List chunks;

    public ProductQueryResultsState(
            String[][] selected,
            String[] onHold,
            String[] removed,
            SampleSelectionSummary prodSummary) 
    {
//        selectedIds = selected;
//        onHoldIds = onHold;
//        removedIds = removed;
    }
    
    public void setCurrentChunk(int i) {
        currentChunk = i;
    }
    public int getCurrentChunk() {
        return currentChunk;
    }
//    public void setSelectedIds(String[][] selids) {
//        selectedIds = selids;
//    }
//    public String[][] getSelectedIds() {
//        return selectedIds;
//    }
//    public void setOnHoldIds(String[] onhold) {
//        onHoldIds = onhold;
//    }
//    public String[] getOnHoldIds() {
//        return onHoldIds;
//    }
//    public void setRemovedIds(String[] removed) {
//        removedIds = removed;
//    }
//    public String[] getRemovedIds() {
//        return removedIds;
//    }

    /**
     * Returns the chunks.
     * @return List
     */
    public List getChunks() {
        return chunks;
    }

    /**
     * Sets the chunks.
     * @param chunks The chunks to set
     */
    public void setChunks(List chunks) {
        this.chunks = chunks;
    }
    
    /**
     * Returns the currentChunkNumber.
     * @return int
     */
    public int getCurrentChunkNumber() {
        return currentChunkNumber;
    }
    
    /**
     * Sets the currentChunkNumber.
     * @param currentChunkNumber The currentChunkNumber to set
     */
    public void setCurrentChunkNumber(int currentChunkNumber) {
        this.currentChunkNumber = currentChunkNumber;
    }

}
