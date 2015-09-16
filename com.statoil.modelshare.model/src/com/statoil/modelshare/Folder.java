/**
 */
package com.statoil.modelshare;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.Folder#getAssets <em>Assets</em>}</li>
 * </ul>
 *
 * @see com.statoil.modelshare.ModelsharePackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends Asset {

	/**
	 * Returns the value of the '<em><b>Assets</b></em>' reference list.
	 * The list contents are of type {@link com.statoil.modelshare.Asset}.
	 * It is bidirectional and its opposite is '{@link com.statoil.modelshare.Asset#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assets</em>' reference list.
	 * @see com.statoil.modelshare.ModelsharePackage#getFolder_Assets()
	 * @see com.statoil.modelshare.Asset#getFolder
	 * @model opposite="folder"
	 * @generated
	 */
	EList<Asset> getAssets();
} // Folder
