package com.kissaki.server.tagDataModel;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-12-27 22:34:42")
/** */
public final class TagDataModelMeta extends org.slim3.datastore.ModelMeta<com.kissaki.server.tagDataModel.TagDataModel> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> m_itemOwnerList = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "m_itemOwnerList", "m_itemOwnerList", java.util.List.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel> m_tagName = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel>(this, "m_tagName", "m_tagName");

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> m_tagOwnerItemList = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.tagDataModel.TagDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "m_tagOwnerItemList", "m_tagOwnerItemList", java.util.List.class);

    private static final TagDataModelMeta slim3_singleton = new TagDataModelMeta();

    /**
     * @return the singleton
     */
    public static TagDataModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public TagDataModelMeta() {
        super("tag", com.kissaki.server.tagDataModel.TagDataModel.class);
    }

    @Override
    public com.kissaki.server.tagDataModel.TagDataModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.kissaki.server.tagDataModel.TagDataModel model = new com.kissaki.server.tagDataModel.TagDataModel();
        model.setKey(entity.getKey());
        model.setM_itemOwnerList(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("m_itemOwnerList")));
        model.setM_tagName((java.lang.String) entity.getProperty("m_tagName"));
        model.setM_tagOwnerItemList(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("m_tagOwnerItemList")));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.kissaki.server.tagDataModel.TagDataModel m = (com.kissaki.server.tagDataModel.TagDataModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("m_itemOwnerList", m.getM_itemOwnerList());
        entity.setProperty("m_tagName", m.getM_tagName());
        entity.setProperty("m_tagOwnerItemList", m.getM_tagOwnerItemList());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.kissaki.server.tagDataModel.TagDataModel m = (com.kissaki.server.tagDataModel.TagDataModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.kissaki.server.tagDataModel.TagDataModel m = (com.kissaki.server.tagDataModel.TagDataModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.kissaki.server.tagDataModel.TagDataModel) is not defined.");
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