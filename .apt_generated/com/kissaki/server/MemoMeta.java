package com.kissaki.server;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-12-15 03:04:36")
/** */
public final class MemoMeta extends org.slim3.datastore.ModelMeta<com.kissaki.server.Memo> {

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.Memo> author = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.Memo>(this, "author", "author");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.Memo, java.util.Date> date = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.Memo, java.util.Date>(this, "date", "date", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.Memo, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.Memo, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    private static final MemoMeta slim3_singleton = new MemoMeta();

    /**
     * @return the singleton
     */
    public static MemoMeta get() {
       return slim3_singleton;
    }

    /** */
    public MemoMeta() {
        super("Mem", com.kissaki.server.Memo.class);
    }

    @Override
    public com.kissaki.server.Memo entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.kissaki.server.Memo model = new com.kissaki.server.Memo();
        model.setAuthor((java.lang.String) entity.getProperty("author"));
        model.setDate((java.util.Date) entity.getProperty("date"));
        model.setKey(entity.getKey());
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.kissaki.server.Memo m = (com.kissaki.server.Memo) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("author", m.getAuthor());
        entity.setProperty("date", m.getDate());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.kissaki.server.Memo m = (com.kissaki.server.Memo) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.kissaki.server.Memo m = (com.kissaki.server.Memo) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.kissaki.server.Memo) is not defined.");
    }

    @Override
    protected void incrementVersion(Object model) {
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

}