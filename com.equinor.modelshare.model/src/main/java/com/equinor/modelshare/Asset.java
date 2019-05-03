/**
 */
package com.equinor.modelshare;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Asset</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.Asset#getName <em>Name</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getPath <em>Path</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getPicturePath <em>Picture Path</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getDescription <em>Description</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getRelativePath <em>Relative Path</em>}</li>
 *   <li>{@link com.equinor.modelshare.Asset#getFormattedDescription <em>Formatted Description</em>}</li>
 * </ul>
 *
 * @see com.equinor.modelshare.ModelsharePackage#getAsset()
 * @model abstract="true"
 * @generated
 */
public interface Asset extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #setName(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_Name()
	 * @model unsettable="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Unsets the value of the '{@link com.equinor.modelshare.Asset#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	void unsetName();

	/**
	 * Returns whether the value of the '{@link com.equinor.modelshare.Asset#getName <em>Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Name</em>' attribute is set.
	 * @see #unsetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	boolean isSetName();

	/**
	 * Returns the value of the '<em><b>Folder</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.equinor.modelshare.Folder#getAssets <em>Assets</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Folder</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder</em>' reference.
	 * @see #setFolder(Folder)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_Folder()
	 * @see com.equinor.modelshare.Folder#getAssets
	 * @model opposite="assets"
	 * @generated
	 */
	Folder getFolder();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getFolder <em>Folder</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' reference.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(Folder value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Absolute file system path to the resource. This is represented in a platform specific format.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #isSetPath()
	 * @see #unsetPath()
	 * @see #setPath(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_Path()
	 * @model unsettable="true" transient="true"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #isSetPath()
	 * @see #unsetPath()
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Unsets the value of the '{@link com.equinor.modelshare.Asset#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPath()
	 * @see #getPath()
	 * @see #setPath(String)
	 * @generated
	 */
	void unsetPath();

	/**
	 * Returns whether the value of the '{@link com.equinor.modelshare.Asset#getPath <em>Path</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Path</em>' attribute is set.
	 * @see #unsetPath()
	 * @see #getPath()
	 * @see #setPath(String)
	 * @generated
	 */
	boolean isSetPath();

	/**
	 * Returns the value of the '<em><b>Picture Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Picture Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Picture Path</em>' attribute.
	 * @see #isSetPicturePath()
	 * @see #unsetPicturePath()
	 * @see #setPicturePath(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_PicturePath()
	 * @model unsettable="true"
	 * @generated
	 */
	String getPicturePath();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getPicturePath <em>Picture Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Picture Path</em>' attribute.
	 * @see #isSetPicturePath()
	 * @see #unsetPicturePath()
	 * @see #getPicturePath()
	 * @generated
	 */
	void setPicturePath(String value);

	/**
	 * Unsets the value of the '{@link com.equinor.modelshare.Asset#getPicturePath <em>Picture Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPicturePath()
	 * @see #getPicturePath()
	 * @see #setPicturePath(String)
	 * @generated
	 */
	void unsetPicturePath();

	/**
	 * Returns whether the value of the '{@link com.equinor.modelshare.Asset#getPicturePath <em>Picture Path</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Picture Path</em>' attribute is set.
	 * @see #unsetPicturePath()
	 * @see #getPicturePath()
	 * @see #setPicturePath(String)
	 * @generated
	 */
	boolean isSetPicturePath();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #isSetDescription()
	 * @see #unsetDescription()
	 * @see #setDescription(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_Description()
	 * @model unsettable="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #isSetDescription()
	 * @see #unsetDescription()
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Unsets the value of the '{@link com.equinor.modelshare.Asset#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDescription()
	 * @see #getDescription()
	 * @see #setDescription(String)
	 * @generated
	 */
	void unsetDescription();

	/**
	 * Returns whether the value of the '{@link com.equinor.modelshare.Asset#getDescription <em>Description</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Description</em>' attribute is set.
	 * @see #unsetDescription()
	 * @see #getDescription()
	 * @see #setDescription(String)
	 * @generated
	 */
	boolean isSetDescription();

	/**
	 * Returns the value of the '<em><b>Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relative Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The relative path to the resource, calculated from the repository root and represented in a platform independent manner.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relative Path</em>' attribute.
	 * @see #isSetRelativePath()
	 * @see #unsetRelativePath()
	 * @see #setRelativePath(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_RelativePath()
	 * @model unsettable="true"
	 * @generated
	 */
	String getRelativePath();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Asset#getRelativePath <em>Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relative Path</em>' attribute.
	 * @see #isSetRelativePath()
	 * @see #unsetRelativePath()
	 * @see #getRelativePath()
	 * @generated
	 */
	void setRelativePath(String value);

	/**
	 * Unsets the value of the '{@link com.equinor.modelshare.Asset#getRelativePath <em>Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRelativePath()
	 * @see #getRelativePath()
	 * @see #setRelativePath(String)
	 * @generated
	 */
	void unsetRelativePath();

	/**
	 * Returns whether the value of the '{@link com.equinor.modelshare.Asset#getRelativePath <em>Relative Path</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Relative Path</em>' attribute is set.
	 * @see #unsetRelativePath()
	 * @see #getRelativePath()
	 * @see #setRelativePath(String)
	 * @generated
	 */
	boolean isSetRelativePath();

	/**
	 * Returns the value of the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formatted Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formatted Description</em>' attribute.
	 * @see com.equinor.modelshare.ModelsharePackage#getAsset_FormattedDescription()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getFormattedDescription();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" accountRequired="true"
	 * @generated
	 */
	EEnum getAccess(Account account);

} // Asset
