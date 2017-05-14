/*
 * Copyright 2014 dorkbox, llc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dorkbox.util.storage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import dorkbox.util.bytes.ByteArrayWrapper;

/**
 * Storage that is in memory only (and is not persisted to disk)
 */
class MemoryStorage implements Storage {
    private final ConcurrentHashMap<ByteArrayWrapper, Object> storage;
    private final ByteArrayWrapper defaultKey;
    private int version;


    MemoryStorage() {
        this.storage = new ConcurrentHashMap<ByteArrayWrapper, Object>();
        this.defaultKey = ByteArrayWrapper.wrap("");
    }


    /**
     * Returns the number of objects in the database.
     */
    @Override
    public
    int size() {
        return storage.size();
    }

    /**
     * Checks if there is a object corresponding to the given key.
     */
    @Override
    public
    boolean contains(final String key) {
        return storage.containsKey(ByteArrayWrapper.wrap(key));
    }

    /**
     * Reads a object using the default (blank) key, and casts it to the expected class
     */
    @SuppressWarnings("unchecked")
    @Override
    public
    <T> T get() {
        return (T) storage.get(defaultKey);
    }

    /**
     * Reads a object using the specific key, and casts it to the expected class
     */
    @Override
    public
    <T> T get(final String key) {
        return get(ByteArrayWrapper.wrap(key));
    }

    /**
     * Reads a object using the specific key, and casts it to the expected class
     */
    @Override
    public
    <T> T get(final byte[] key) {
        return get(ByteArrayWrapper.wrap(key));
    }

    /**
     * Reads a object using the specific key, and casts it to the expected class
     */
    @SuppressWarnings("unchecked")
    @Override
    public
    <T> T get(final ByteArrayWrapper key) {
        return (T) storage.get(key);
    }

    /**
     * Uses the DEFAULT key ("") to return saved data.
     * <p/>
     * This will check to see if there is an associated key for that data, if not - it will use data as the default
     *
     * @param data The data that will hold the copy of the data from disk
     */
    @Override
    public
    <T> T getAndPut(T data) throws IOException {
        return getAndPut(this.defaultKey, data);
    }

    /**
     * Returns the saved data for the specified key.
     *
     * @param data If there is no object in the DB with the specified key, this value will be the default (and will be saved to the db)
     */
    @Override
    public
    <T> T getAndPut(String key, T data) throws IOException {
        ByteArrayWrapper wrap = ByteArrayWrapper.wrap(key);

        return getAndPut(wrap, data);
    }

    /**
     * Returns the saved data for the specified key.
     *
     * @param data If there is no object in the DB with the specified key, this value will be the default (and will be saved to the db)
     */
    @Override
    public
    <T> T getAndPut(byte[] key, T data) throws IOException {
        return getAndPut(ByteArrayWrapper.wrap(key), data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public
    <T> T getAndPut(final ByteArrayWrapper key, final T data) throws IOException {
        final Object o = storage.get(key);
        if (o == null) {
            storage.put(key, data);
            return data;
        }
        return (T) o;
    }

    /**
     * Saves the given data to storage with the associated key.
     * <p/>
     * Also will update existing data. If the new contents do not fit in the original space, then the update is handled by
     * deleting the old data and adding the new.
     */
    @Override
    public
    void put(final String key, final Object data) {
        put(ByteArrayWrapper.wrap(key), data);
    }

    /**
     * Saves the given data to storage with the associated key.
     * <p/>
     * Also will update existing data. If the new contents do not fit in the original space, then the update is handled by
     * deleting the old data and adding the new.
     */
    @Override
    public
    void put(final byte[] key, final Object data) {
        put(ByteArrayWrapper.wrap(key), data);
    }

    /**
     * Saves the given data to storage with the associated key.
     * <p/>
     * Also will update existing data. If the new contents do not fit in the original space, then the update is handled by
     * deleting the old data and adding the new.
     */
    @Override
    public
    void put(final ByteArrayWrapper key, final Object object) {
        storage.put(key, object);
    }

    /**
     * Saves the given data to storage with the associated key.
     * <p/>
     * Also will update existing data. If the new contents do not fit in the original space, then the update is handled by
     * deleting the old data and adding the new.
     */
    @Override
    public
    void put(final Object data) {
        put(defaultKey, data);
    }

    /**
     * Deletes an object from storage.
     *
     * @return true if the delete was successful. False if there were problems deleting the data.
     */
    @Override
    public
    boolean delete(final String key) {
        return delete(ByteArrayWrapper.wrap(key));
    }

    /**
     * Deletes an object from storage.
     *
     * @return true if the delete was successful. False if there were problems deleting the data.
     */
    @Override
    public
    boolean delete(final ByteArrayWrapper key) {
        storage.remove(key);
        return true;
    }

    @Override
    public
    File getFile() {
        return null;
    }

    @Override
    public
    long getFileSize() {
        return 0;
    }

    @Override
    public
    boolean hasWriteWaiting() {
        return false;
    }

    @Override
    public
    long getSaveDelay() {
        return 0;
    }

    @Override
    public
    void setSaveDelay(final long milliSeconds) {
    }

    @Override
    public synchronized
    int getVersion() {
        return version;
    }

    @Override
    public synchronized
    void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public
    void save() {
        // no-op
    }

    @Override
    public
    void putAndSave(final String key, final Object object) {
        // no-op
    }

    @Override
    public
    void putAndSave(final byte[] key, final Object object) {
        // no-op
    }

    @Override
    public
    void putAndSave(final ByteArrayWrapper key, final Object object) {
        // no-op
    }
}