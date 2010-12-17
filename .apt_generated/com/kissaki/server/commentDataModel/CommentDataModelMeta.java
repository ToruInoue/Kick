package com.kissaki.server.commentDataModel;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-12-17 02:00:41")
/** */
public final class CommentDataModelMeta extends org.slim3.datastore.ModelMeta<com.kissaki.server.commentDataModel.CommentDataModel> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel> m_commentBody = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel>(this, "m_commentBody", "m_commentBody");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, java.util.Date> m_commentDate = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, java.util.Date>(this, "m_commentDate", "m_commentDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key> m_commentMasterID = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key>(this, "m_commentMasterID", "m_commentMasterID", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key> m_commentedBy = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.commentDataModel.CommentDataModel, com.google.appengine.api.datastore.Key>(this, "m_commentedBy", "m_commentedBy", com.google.appengine.api.datastore.Key.class);

    private static final CommentDataModelMeta slim3_singleton = new CommentDataModelMeta();

    /**
     * @return the singleton
     */
    public static CommentDataModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public CommentDataModelMeta() {
        super("comment", com.kissaki.server.commentDataModel.CommentDataModel.class);
    }

    @Override
    public com.kissaki.server.commentDataModel.CommentDataModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.kissaki.server.commentDataModel.CommentDataModel model = new com.kissaki.server.commentDataModel.CommentDataModel();
        model.setKey(entity.getKey());
        model.setM_commentBody((java.lang.String) entity.getProperty("m_commentBody"));
        model.setM_commentDate((java.util.Date) entity.getProperty("m_commentDate"));
        model.setM_commentMasterID((com.google.appengine.api.datastore.Key) entity.getProperty("m_commentMasterID"));
        model.setM_commentedBy((com.google.appengine.api.datastore.Key) entity.getProperty("m_commentedBy"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.kissaki.server.commentDataModel.CommentDataModel m = (com.kissaki.server.commentDataModel.CommentDataModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("m_commentBody", m.getM_commentBody());
        entity.setProperty("m_commentDate", m.getM_commentDate());
        entity.setProperty("m_commentMasterID", m.getM_commentMasterID());
        entity.setProperty("m_commentedBy", m.getM_commentedBy());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.kissaki.server.commentDataModel.CommentDataModel m = (com.kissaki.server.commentDataModel.CommentDataModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.kissaki.server.commentDataModel.CommentDataModel m = (com.kissaki.server.commentDataModel.CommentDataModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.kissaki.server.commentDataModel.CommentDataModel) is not defined.");
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