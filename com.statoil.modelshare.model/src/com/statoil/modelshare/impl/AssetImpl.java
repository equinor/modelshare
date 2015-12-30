/**
 */
package com.statoil.modelshare.impl;

import com.statoil.modelshare.Account;
import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.ModelsharePackage;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.markdown.core.MarkdownLanguage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Asset</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getPicturePath <em>Picture Path</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getRelativePath <em>Relative Path</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AssetImpl#getFormattedDescription <em>Formatted Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AssetImpl extends MinimalEObjectImpl.Container implements Asset {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * This is true if the Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean nameESet;
	/**
	 * The cached value of the '{@link #getFolder() <em>Folder</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected Folder folder;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * This is true if the Path attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean pathESet;
	/**
	 * The default value of the '{@link #getPicturePath() <em>Picture Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPicturePath()
	 * @generated
	 * @ordered
	 */
	protected static final String PICTURE_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPicturePath() <em>Picture Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPicturePath()
	 * @generated
	 * @ordered
	 */
	protected String picturePath = PICTURE_PATH_EDEFAULT;

	/**
	 * This is true if the Picture Path attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean picturePathESet;
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;
	/**
	 * This is true if the Description attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean descriptionESet;
	/**
	 * The default value of the '{@link #getRelativePath() <em>Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePath()
	 * @generated
	 * @ordered
	 */
	protected static final String RELATIVE_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getRelativePath() <em>Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePath()
	 * @generated
	 * @ordered
	 */
	protected String relativePath = RELATIVE_PATH_EDEFAULT;

	/**
	 * This is true if the Relative Path attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean relativePathESet;

	/**
	 * The default value of the '{@link #getFormattedDescription() <em>Formatted Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormattedDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMATTED_DESCRIPTION_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.ASSET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		boolean oldNameESet = nameESet;
		nameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__NAME, oldName, name, !oldNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetName() {
		String oldName = name;
		boolean oldNameESet = nameESet;
		name = NAME_EDEFAULT;
		nameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelsharePackage.ASSET__NAME, oldName, NAME_EDEFAULT, oldNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetName() {
		return nameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Folder getFolder() {
		if (folder != null && folder.eIsProxy()) {
			InternalEObject oldFolder = (InternalEObject)folder;
			folder = (Folder)eResolveProxy(oldFolder);
			if (folder != oldFolder) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelsharePackage.ASSET__FOLDER, oldFolder, folder));
			}
		}
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Folder basicGetFolder() {
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFolder(Folder newFolder, NotificationChain msgs) {
		Folder oldFolder = folder;
		folder = newFolder;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__FOLDER, oldFolder, newFolder);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFolder(Folder newFolder) {
		if (newFolder != folder) {
			NotificationChain msgs = null;
			if (folder != null)
				msgs = ((InternalEObject)folder).eInverseRemove(this, ModelsharePackage.FOLDER__ASSETS, Folder.class, msgs);
			if (newFolder != null)
				msgs = ((InternalEObject)newFolder).eInverseAdd(this, ModelsharePackage.FOLDER__ASSETS, Folder.class, msgs);
			msgs = basicSetFolder(newFolder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__FOLDER, newFolder, newFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		boolean oldPathESet = pathESet;
		pathESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__PATH, oldPath, path, !oldPathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPath() {
		String oldPath = path;
		boolean oldPathESet = pathESet;
		path = PATH_EDEFAULT;
		pathESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelsharePackage.ASSET__PATH, oldPath, PATH_EDEFAULT, oldPathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPath() {
		return pathESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPicturePath(String newPicturePath) {
		String oldPicturePath = picturePath;
		picturePath = newPicturePath;
		boolean oldPicturePathESet = picturePathESet;
		picturePathESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__PICTURE_PATH, oldPicturePath, picturePath, !oldPicturePathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPicturePath() {
		String oldPicturePath = picturePath;
		boolean oldPicturePathESet = picturePathESet;
		picturePath = PICTURE_PATH_EDEFAULT;
		picturePathESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelsharePackage.ASSET__PICTURE_PATH, oldPicturePath, PICTURE_PATH_EDEFAULT, oldPicturePathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPicturePath() {
		return picturePathESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		boolean oldDescriptionESet = descriptionESet;
		descriptionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDescription() {
		String oldDescription = description;
		boolean oldDescriptionESet = descriptionESet;
		description = DESCRIPTION_EDEFAULT;
		descriptionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelsharePackage.ASSET__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDescription() {
		return descriptionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRelativePath() {
		return relativePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelativePath(String newRelativePath) {
		String oldRelativePath = relativePath;
		relativePath = newRelativePath;
		boolean oldRelativePathESet = relativePathESet;
		relativePathESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ASSET__RELATIVE_PATH, oldRelativePath, relativePath, !oldRelativePathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRelativePath() {
		String oldRelativePath = relativePath;
		boolean oldRelativePathESet = relativePathESet;
		relativePath = RELATIVE_PATH_EDEFAULT;
		relativePathESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelsharePackage.ASSET__RELATIVE_PATH, oldRelativePath, RELATIVE_PATH_EDEFAULT, oldRelativePathESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRelativePath() {
		return relativePathESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFormattedDescription() {
		if (getDescription()!=null){
			StringWriter sw = new StringWriter();
			MarkupParser parser = new MarkupParser();
			parser.setMarkupLanguage(new MarkdownLanguage());
			HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
			builder.setEmitAsDocument(false);
			parser.setBuilder(builder);
			try {
				parser.parse(new StringReader(getDescription()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sw.toString();
		} else return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAccess(Account account) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelsharePackage.ASSET__FOLDER:
				if (folder != null)
					msgs = ((InternalEObject)folder).eInverseRemove(this, ModelsharePackage.FOLDER__ASSETS, Folder.class, msgs);
				return basicSetFolder((Folder)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelsharePackage.ASSET__FOLDER:
				return basicSetFolder(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelsharePackage.ASSET__NAME:
				return getName();
			case ModelsharePackage.ASSET__FOLDER:
				if (resolve) return getFolder();
				return basicGetFolder();
			case ModelsharePackage.ASSET__PATH:
				return getPath();
			case ModelsharePackage.ASSET__PICTURE_PATH:
				return getPicturePath();
			case ModelsharePackage.ASSET__DESCRIPTION:
				return getDescription();
			case ModelsharePackage.ASSET__RELATIVE_PATH:
				return getRelativePath();
			case ModelsharePackage.ASSET__FORMATTED_DESCRIPTION:
				return getFormattedDescription();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelsharePackage.ASSET__NAME:
				setName((String)newValue);
				return;
			case ModelsharePackage.ASSET__FOLDER:
				setFolder((Folder)newValue);
				return;
			case ModelsharePackage.ASSET__PATH:
				setPath((String)newValue);
				return;
			case ModelsharePackage.ASSET__PICTURE_PATH:
				setPicturePath((String)newValue);
				return;
			case ModelsharePackage.ASSET__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ModelsharePackage.ASSET__RELATIVE_PATH:
				setRelativePath((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelsharePackage.ASSET__NAME:
				unsetName();
				return;
			case ModelsharePackage.ASSET__FOLDER:
				setFolder((Folder)null);
				return;
			case ModelsharePackage.ASSET__PATH:
				unsetPath();
				return;
			case ModelsharePackage.ASSET__PICTURE_PATH:
				unsetPicturePath();
				return;
			case ModelsharePackage.ASSET__DESCRIPTION:
				unsetDescription();
				return;
			case ModelsharePackage.ASSET__RELATIVE_PATH:
				unsetRelativePath();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelsharePackage.ASSET__NAME:
				return isSetName();
			case ModelsharePackage.ASSET__FOLDER:
				return folder != null;
			case ModelsharePackage.ASSET__PATH:
				return isSetPath();
			case ModelsharePackage.ASSET__PICTURE_PATH:
				return isSetPicturePath();
			case ModelsharePackage.ASSET__DESCRIPTION:
				return isSetDescription();
			case ModelsharePackage.ASSET__RELATIVE_PATH:
				return isSetRelativePath();
			case ModelsharePackage.ASSET__FORMATTED_DESCRIPTION:
				return FORMATTED_DESCRIPTION_EDEFAULT == null ? getFormattedDescription() != null : !FORMATTED_DESCRIPTION_EDEFAULT.equals(getFormattedDescription());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ModelsharePackage.ASSET___GET_ACCESS__ACCOUNT:
				return getAccess((Account)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		if (nameESet) result.append(name); else result.append("<unset>");
		result.append(", path: ");
		if (pathESet) result.append(path); else result.append("<unset>");
		result.append(", picturePath: ");
		if (picturePathESet) result.append(picturePath); else result.append("<unset>");
		result.append(", description: ");
		if (descriptionESet) result.append(description); else result.append("<unset>");
		result.append(", relativePath: ");
		if (relativePathESet) result.append(relativePath); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AssetImpl
