package com.kissaki.server.userDataModel;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-12-10 12:31:48")
/** */
public final class UserDataModelMeta extends org.slim3.datastore.ModelMeta<com.kissaki.server.userDataModel.UserDataModel> {

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> itemKeys = new org.slim3.datastore.CollectionAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "itemKeys", "itemKeys", java.util.List.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.userDataModel.UserDataModel> m_userName = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.userDataModel.UserDataModel>(this, "m_userName", "m_userName");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.kissaki.server.userDataModel.UserDataModel> m_userPass = new org.slim3.datastore.StringAttributeMeta<com.kissaki.server.userDataModel.UserDataModel>(this, "m_userPass", "m_userPass");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, java.lang.Integer> m_userStatus = new org.slim3.datastore.CoreAttributeMeta<com.kissaki.server.userDataModel.UserDataModel, java.lang.Integer>(this, "m_userStatus", "m_userStatus", int.class);

    private static final UserDataModelMeta slim3_singleton = new UserDataModelMeta();

    /**
     * @return the singleton
     */
    public static UserDataModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public UserDataModelMeta() {
        super("user", com.kissaki.server.userDataModel.UserDataModel.class);
    }

    @Override
    public com.kissaki.server.userDataModel.UserDataModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.kissaki.server.userDataModel.UserDataModel model = new com.kissaki.server.userDataModel.UserDataModel();
        model.setItemKeys(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("itemKeys")));
        model.setKey(entity.getKey());
        model.setM_userName((java.lang.String) entity.getProperty("m_userName"));
        model.setM_userPass((java.lang.String) entity.getProperty("m_userPass"));
        model.setM_userStatus(longToPrimitiveInt((java.lang.Long) entity.getProperty("m_userStatus")));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.kissaki.server.userDataModel.UserDataModel m = (com.kissaki.server.userDataModel.UserDataModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("itemKeys", m.getItemKeys());
        entity.setProperty("m_userName", m.getM_userName());
        entity.setProperty("m_userPass", m.getM_userPass());
        entity.setProperty("m_userStatus", m.getM_userStatus());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.kissaki.server.userDataModel.UserDataModel m = (com.kissaki.server.userDataModel.UserDataModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.kissaki.server.userDataModel.UserDataModel m = (com.kissaki.server.userDataModel.UserDataModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.kissaki.server.userDataModel.UserDataModel) is not defined.");
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