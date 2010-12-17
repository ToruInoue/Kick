package com.kissaki.server.itemDataModel;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-12-15 17:23:49")
/** */
public final class ItemDataModelMeta extends org.slim3.datastore.ModelMeta<com.kissaki.server.itemDataModel.ItemDataModel> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> m_commentList = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "m_commentList", "m_commentList", java.util.List.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel> m_itemName = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel>(this, "m_itemName", "m_itemName");

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> m_ownerList = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "m_ownerList", "m_ownerList", java.util.List.class);

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> m_tagList = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.itemDataModel.ItemDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "m_tagList", "m_tagList", java.util.List.class);

    private static final ItemDataModelMeta slim3_singleton = new ItemDataModelMeta();

    /**
     * @return the singleton
     */
    public static ItemDataModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public ItemDataModelMeta() {
        super("item", com.kissaki.server.itemDataModel.ItemDataModel.class);
    }

    @Override
    public com.kissaki.server.itemDataModel.ItemDataModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.kissaki.server.itemDataModel.ItemDataModel model = new com.kissaki.server.itemDataModel.ItemDataModel();
        model.setKey(entity.getKey());
        model.setM_commentList(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("m_commentList")));
        model.setM_itemName((java.lang.String) entity.getProperty("m_itemName"));
        model.setM_ownerList(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("m_ownerList")));
        model.setM_tagList(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("m_tagList")));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.kissaki.server.itemDataModel.ItemDataModel m = (com.kissaki.server.itemDataModel.ItemDataModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("m_commentList", m.getM_commentList());
        entity.setProperty("m_itemName", m.getM_itemName());
        entity.setProperty("m_ownerList", m.getM_ownerList());
        entity.setProperty("m_tagList", m.getM_tagList());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.kissaki.server.itemDataModel.ItemDataModel m = (com.kissaki.server.itemDataModel.ItemDataModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.kissaki.server.itemDataModel.ItemDataModel m = (com.kissaki.server.itemDataModel.ItemDataModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.kissaki.server.itemDataModel.ItemDataModel) is not defined.");
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