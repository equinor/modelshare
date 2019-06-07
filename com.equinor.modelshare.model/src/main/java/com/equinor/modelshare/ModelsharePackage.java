/**
 */
package com.equinor.modelshare;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.equinor.modelshare.ModelshareFactory
 * @model kind="package"
 * @generated
 */
public interface ModelsharePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "modelshare";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.equinor.com/modelshare";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.equinor.modelshare.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelsharePackage eINSTANCE = com.equinor.modelshare.impl.ModelsharePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.AccountImpl <em>Account</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.AccountImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAccount()
	 * @generated
	 */
	int ACCOUNT = 3;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__GROUP = 2;

	/**
	 * The number of structural features of the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get All Roles</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT___GET_ALL_ROLES = 0;

	/**
	 * The number of operations of the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.UserImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getUser()
	 * @generated
	 */
	int USER = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__IDENTIFIER = ACCOUNT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__NAME = ACCOUNT__NAME;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__GROUP = ACCOUNT__GROUP;

	/**
	 * The feature id for the '<em><b>Organisation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__ORGANISATION = ACCOUNT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__EMAIL = ACCOUNT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__PASSWORD = ACCOUNT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Force Change Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__FORCE_CHANGE_PASSWORD = ACCOUNT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Local User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__LOCAL_USER = ACCOUNT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Resettoken</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__RESETTOKEN = ACCOUNT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = ACCOUNT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get All Roles</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER___GET_ALL_ROLES = ACCOUNT___GET_ALL_ROLES;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = ACCOUNT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.AssetImpl <em>Asset</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.AssetImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAsset()
	 * @generated
	 */
	int ASSET = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__NAME = 0;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__FOLDER = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__PATH = 2;

	/**
	 * The feature id for the '<em><b>Picture Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__PICTURE_PATH = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__RELATIVE_PATH = 5;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__FORMATTED_DESCRIPTION = 6;

	/**
	 * The number of structural features of the '<em>Asset</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_FEATURE_COUNT = 7;

	/**
	 * The operation id for the '<em>Get Access</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET___GET_ACCESS__ACCOUNT = 0;

	/**
	 * The number of operations of the '<em>Asset</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.ModelImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NAME = ASSET__NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__FOLDER = ASSET__FOLDER;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__PATH = ASSET__PATH;

	/**
	 * The feature id for the '<em><b>Picture Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__PICTURE_PATH = ASSET__PICTURE_PATH;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__DESCRIPTION = ASSET__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__RELATIVE_PATH = ASSET__RELATIVE_PATH;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__FORMATTED_DESCRIPTION = ASSET__FORMATTED_DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__OWNER = ASSET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Organisation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__ORGANISATION = ASSET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__MAIL = ASSET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Last Updated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__LAST_UPDATED = ASSET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Stask</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__STASK = ASSET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Task Details</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__TASK_DETAILS = ASSET_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Usage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__USAGE = ASSET_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Task Folders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__TASK_FOLDERS = ASSET_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Sima Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__SIMA_VERSION = ASSET_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = ASSET_FEATURE_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Access</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL___GET_ACCESS__ACCOUNT = ASSET___GET_ACCESS__ACCOUNT;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_OPERATION_COUNT = ASSET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.GroupImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 2;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__IDENTIFIER = ACCOUNT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__NAME = ACCOUNT__NAME;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP = ACCOUNT__GROUP;

	/**
	 * The feature id for the '<em><b>External Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__EXTERNAL_IDENTIFIER = ACCOUNT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = ACCOUNT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Roles</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP___GET_ALL_ROLES = ACCOUNT___GET_ALL_ROLES;

	/**
	 * The number of operations of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_OPERATION_COUNT = ACCOUNT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.FolderImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = ASSET__NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__FOLDER = ASSET__FOLDER;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__PATH = ASSET__PATH;

	/**
	 * The feature id for the '<em><b>Picture Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__PICTURE_PATH = ASSET__PICTURE_PATH;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__DESCRIPTION = ASSET__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__RELATIVE_PATH = ASSET__RELATIVE_PATH;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__FORMATTED_DESCRIPTION = ASSET__FORMATTED_DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Assets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ASSETS = ASSET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = ASSET_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Access</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER___GET_ACCESS__ACCOUNT = ASSET___GET_ACCESS__ACCOUNT;

	/**
	 * The number of operations of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_OPERATION_COUNT = ASSET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.AbstractTaskImpl <em>Abstract Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.AbstractTaskImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAbstractTask()
	 * @generated
	 */
	int ABSTRACT_TASK = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Formatted Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__FORMATTED_NAME = 2;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__FORMATTED_DESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Abstract Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Abstract Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.TaskDetailsImpl <em>Task Details</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.TaskDetailsImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getTaskDetails()
	 * @generated
	 */
	int TASK_DETAILS = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS__NAME = ABSTRACT_TASK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS__DESCRIPTION = ABSTRACT_TASK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Formatted Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS__FORMATTED_NAME = ABSTRACT_TASK__FORMATTED_NAME;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS__FORMATTED_DESCRIPTION = ABSTRACT_TASK__FORMATTED_DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS__IDENTIFIER = ABSTRACT_TASK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Task Details</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Task Details</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DETAILS_OPERATION_COUNT = ABSTRACT_TASK_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.TaskFolderImpl <em>Task Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.TaskFolderImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getTaskFolder()
	 * @generated
	 */
	int TASK_FOLDER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER__NAME = ABSTRACT_TASK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER__DESCRIPTION = ABSTRACT_TASK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Formatted Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER__FORMATTED_NAME = ABSTRACT_TASK__FORMATTED_NAME;

	/**
	 * The feature id for the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER__FORMATTED_DESCRIPTION = ABSTRACT_TASK__FORMATTED_DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Task Details</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER__TASK_DETAILS = ABSTRACT_TASK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Task Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Task Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FOLDER_OPERATION_COUNT = ABSTRACT_TASK_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.TokenImpl <em>Token</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.TokenImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getToken()
	 * @generated
	 */
	int TOKEN = 9;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN__KEY = 0;

	/**
	 * The feature id for the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN__CREATED = 1;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN__USER = 2;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN__TIMEOUT = 3;

	/**
	 * The number of structural features of the '<em>Token</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Token</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOKEN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.impl.PageImpl
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 10;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TITLE = 1;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.equinor.modelshare.Access <em>Access</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.equinor.modelshare.Access
	 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAccess()
	 * @generated
	 */
	int ACCESS = 11;


	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see com.equinor.modelshare.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.User#getOrganisation <em>Organisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organisation</em>'.
	 * @see com.equinor.modelshare.User#getOrganisation()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Organisation();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.User#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see com.equinor.modelshare.User#getEmail()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Email();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.User#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.equinor.modelshare.User#getPassword()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.User#isForceChangePassword <em>Force Change Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Force Change Password</em>'.
	 * @see com.equinor.modelshare.User#isForceChangePassword()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_ForceChangePassword();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.User#getLocalUser <em>Local User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local User</em>'.
	 * @see com.equinor.modelshare.User#getLocalUser()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_LocalUser();

	/**
	 * Returns the meta object for the reference '{@link com.equinor.modelshare.User#getResettoken <em>Resettoken</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resettoken</em>'.
	 * @see com.equinor.modelshare.User#getResettoken()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Resettoken();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see com.equinor.modelshare.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner</em>'.
	 * @see com.equinor.modelshare.Model#getOwner()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Owner();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getOrganisation <em>Organisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organisation</em>'.
	 * @see com.equinor.modelshare.Model#getOrganisation()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Organisation();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getMail <em>Mail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mail</em>'.
	 * @see com.equinor.modelshare.Model#getMail()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Mail();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getLastUpdated <em>Last Updated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Updated</em>'.
	 * @see com.equinor.modelshare.Model#getLastUpdated()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_LastUpdated();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#isStask <em>Stask</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stask</em>'.
	 * @see com.equinor.modelshare.Model#isStask()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Stask();

	/**
	 * Returns the meta object for the containment reference list '{@link com.equinor.modelshare.Model#getTaskDetails <em>Task Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Task Details</em>'.
	 * @see com.equinor.modelshare.Model#getTaskDetails()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_TaskDetails();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getUsage <em>Usage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Usage</em>'.
	 * @see com.equinor.modelshare.Model#getUsage()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Usage();

	/**
	 * Returns the meta object for the containment reference list '{@link com.equinor.modelshare.Model#getTaskFolders <em>Task Folders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Task Folders</em>'.
	 * @see com.equinor.modelshare.Model#getTaskFolders()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_TaskFolders();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Model#getSimaVersion <em>Sima Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sima Version</em>'.
	 * @see com.equinor.modelshare.Model#getSimaVersion()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_SimaVersion();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see com.equinor.modelshare.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Group#getExternalIdentifier <em>External Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>External Identifier</em>'.
	 * @see com.equinor.modelshare.Group#getExternalIdentifier()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_ExternalIdentifier();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Account <em>Account</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Account</em>'.
	 * @see com.equinor.modelshare.Account
	 * @generated
	 */
	EClass getAccount();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Account#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see com.equinor.modelshare.Account#getIdentifier()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Account#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.equinor.modelshare.Account#getName()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_Name();

	/**
	 * Returns the meta object for the reference '{@link com.equinor.modelshare.Account#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Group</em>'.
	 * @see com.equinor.modelshare.Account#getGroup()
	 * @see #getAccount()
	 * @generated
	 */
	EReference getAccount_Group();

	/**
	 * Returns the meta object for the '{@link com.equinor.modelshare.Account#getAllRoles() <em>Get All Roles</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Roles</em>' operation.
	 * @see com.equinor.modelshare.Account#getAllRoles()
	 * @generated
	 */
	EOperation getAccount__GetAllRoles();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.equinor.modelshare.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for the reference list '{@link com.equinor.modelshare.Folder#getAssets <em>Assets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Assets</em>'.
	 * @see com.equinor.modelshare.Folder#getAssets()
	 * @see #getFolder()
	 * @generated
	 */
	EReference getFolder_Assets();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Asset <em>Asset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asset</em>'.
	 * @see com.equinor.modelshare.Asset
	 * @generated
	 */
	EClass getAsset();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.equinor.modelshare.Asset#getName()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_Name();

	/**
	 * Returns the meta object for the reference '{@link com.equinor.modelshare.Asset#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Folder</em>'.
	 * @see com.equinor.modelshare.Asset#getFolder()
	 * @see #getAsset()
	 * @generated
	 */
	EReference getAsset_Folder();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.equinor.modelshare.Asset#getPath()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getPicturePath <em>Picture Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Picture Path</em>'.
	 * @see com.equinor.modelshare.Asset#getPicturePath()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_PicturePath();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.equinor.modelshare.Asset#getDescription()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getRelativePath <em>Relative Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relative Path</em>'.
	 * @see com.equinor.modelshare.Asset#getRelativePath()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_RelativePath();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Asset#getFormattedDescription <em>Formatted Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formatted Description</em>'.
	 * @see com.equinor.modelshare.Asset#getFormattedDescription()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_FormattedDescription();

	/**
	 * Returns the meta object for the '{@link com.equinor.modelshare.Asset#getAccess(com.equinor.modelshare.Account) <em>Get Access</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Access</em>' operation.
	 * @see com.equinor.modelshare.Asset#getAccess(com.equinor.modelshare.Account)
	 * @generated
	 */
	EOperation getAsset__GetAccess__Account();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.TaskDetails <em>Task Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Details</em>'.
	 * @see com.equinor.modelshare.TaskDetails
	 * @generated
	 */
	EClass getTaskDetails();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.TaskDetails#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see com.equinor.modelshare.TaskDetails#getIdentifier()
	 * @see #getTaskDetails()
	 * @generated
	 */
	EAttribute getTaskDetails_Identifier();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.AbstractTask <em>Abstract Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Task</em>'.
	 * @see com.equinor.modelshare.AbstractTask
	 * @generated
	 */
	EClass getAbstractTask();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.AbstractTask#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.equinor.modelshare.AbstractTask#getName()
	 * @see #getAbstractTask()
	 * @generated
	 */
	EAttribute getAbstractTask_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.AbstractTask#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.equinor.modelshare.AbstractTask#getDescription()
	 * @see #getAbstractTask()
	 * @generated
	 */
	EAttribute getAbstractTask_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.AbstractTask#getFormattedName <em>Formatted Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formatted Name</em>'.
	 * @see com.equinor.modelshare.AbstractTask#getFormattedName()
	 * @see #getAbstractTask()
	 * @generated
	 */
	EAttribute getAbstractTask_FormattedName();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.AbstractTask#getFormattedDescription <em>Formatted Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formatted Description</em>'.
	 * @see com.equinor.modelshare.AbstractTask#getFormattedDescription()
	 * @see #getAbstractTask()
	 * @generated
	 */
	EAttribute getAbstractTask_FormattedDescription();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.TaskFolder <em>Task Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Folder</em>'.
	 * @see com.equinor.modelshare.TaskFolder
	 * @generated
	 */
	EClass getTaskFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link com.equinor.modelshare.TaskFolder#getTaskDetails <em>Task Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Task Details</em>'.
	 * @see com.equinor.modelshare.TaskFolder#getTaskDetails()
	 * @see #getTaskFolder()
	 * @generated
	 */
	EReference getTaskFolder_TaskDetails();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Token <em>Token</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Token</em>'.
	 * @see com.equinor.modelshare.Token
	 * @generated
	 */
	EClass getToken();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Token#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.equinor.modelshare.Token#getKey()
	 * @see #getToken()
	 * @generated
	 */
	EAttribute getToken_Key();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Token#getCreated <em>Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created</em>'.
	 * @see com.equinor.modelshare.Token#getCreated()
	 * @see #getToken()
	 * @generated
	 */
	EAttribute getToken_Created();

	/**
	 * Returns the meta object for the reference '{@link com.equinor.modelshare.Token#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User</em>'.
	 * @see com.equinor.modelshare.Token#getUser()
	 * @see #getToken()
	 * @generated
	 */
	EReference getToken_User();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Token#getTimeout <em>Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout</em>'.
	 * @see com.equinor.modelshare.Token#getTimeout()
	 * @see #getToken()
	 * @generated
	 */
	EAttribute getToken_Timeout();

	/**
	 * Returns the meta object for class '{@link com.equinor.modelshare.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see com.equinor.modelshare.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Page#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.equinor.modelshare.Page#getLocation()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.equinor.modelshare.Page#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.equinor.modelshare.Page#getTitle()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Title();

	/**
	 * Returns the meta object for enum '{@link com.equinor.modelshare.Access <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access</em>'.
	 * @see com.equinor.modelshare.Access
	 * @generated
	 */
	EEnum getAccess();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelshareFactory getModelshareFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.UserImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Organisation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__ORGANISATION = eINSTANCE.getUser_Organisation();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__EMAIL = eINSTANCE.getUser_Email();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__PASSWORD = eINSTANCE.getUser_Password();

		/**
		 * The meta object literal for the '<em><b>Force Change Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__FORCE_CHANGE_PASSWORD = eINSTANCE.getUser_ForceChangePassword();

		/**
		 * The meta object literal for the '<em><b>Local User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__LOCAL_USER = eINSTANCE.getUser_LocalUser();

		/**
		 * The meta object literal for the '<em><b>Resettoken</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__RESETTOKEN = eINSTANCE.getUser_Resettoken();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.ModelImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__OWNER = eINSTANCE.getModel_Owner();

		/**
		 * The meta object literal for the '<em><b>Organisation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__ORGANISATION = eINSTANCE.getModel_Organisation();

		/**
		 * The meta object literal for the '<em><b>Mail</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__MAIL = eINSTANCE.getModel_Mail();

		/**
		 * The meta object literal for the '<em><b>Last Updated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__LAST_UPDATED = eINSTANCE.getModel_LastUpdated();

		/**
		 * The meta object literal for the '<em><b>Stask</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__STASK = eINSTANCE.getModel_Stask();

		/**
		 * The meta object literal for the '<em><b>Task Details</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__TASK_DETAILS = eINSTANCE.getModel_TaskDetails();

		/**
		 * The meta object literal for the '<em><b>Usage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__USAGE = eINSTANCE.getModel_Usage();

		/**
		 * The meta object literal for the '<em><b>Task Folders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__TASK_FOLDERS = eINSTANCE.getModel_TaskFolders();

		/**
		 * The meta object literal for the '<em><b>Sima Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__SIMA_VERSION = eINSTANCE.getModel_SimaVersion();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.GroupImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>External Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__EXTERNAL_IDENTIFIER = eINSTANCE.getGroup_ExternalIdentifier();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.AccountImpl <em>Account</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.AccountImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAccount()
		 * @generated
		 */
		EClass ACCOUNT = eINSTANCE.getAccount();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__IDENTIFIER = eINSTANCE.getAccount_Identifier();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__NAME = eINSTANCE.getAccount_Name();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNT__GROUP = eINSTANCE.getAccount_Group();

		/**
		 * The meta object literal for the '<em><b>Get All Roles</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ACCOUNT___GET_ALL_ROLES = eINSTANCE.getAccount__GetAllRoles();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.FolderImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '<em><b>Assets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOLDER__ASSETS = eINSTANCE.getFolder_Assets();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.AssetImpl <em>Asset</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.AssetImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAsset()
		 * @generated
		 */
		EClass ASSET = eINSTANCE.getAsset();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__NAME = eINSTANCE.getAsset_Name();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSET__FOLDER = eINSTANCE.getAsset_Folder();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__PATH = eINSTANCE.getAsset_Path();

		/**
		 * The meta object literal for the '<em><b>Picture Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__PICTURE_PATH = eINSTANCE.getAsset_PicturePath();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__DESCRIPTION = eINSTANCE.getAsset_Description();

		/**
		 * The meta object literal for the '<em><b>Relative Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__RELATIVE_PATH = eINSTANCE.getAsset_RelativePath();

		/**
		 * The meta object literal for the '<em><b>Formatted Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__FORMATTED_DESCRIPTION = eINSTANCE.getAsset_FormattedDescription();

		/**
		 * The meta object literal for the '<em><b>Get Access</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASSET___GET_ACCESS__ACCOUNT = eINSTANCE.getAsset__GetAccess__Account();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.TaskDetailsImpl <em>Task Details</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.TaskDetailsImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getTaskDetails()
		 * @generated
		 */
		EClass TASK_DETAILS = eINSTANCE.getTaskDetails();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_DETAILS__IDENTIFIER = eINSTANCE.getTaskDetails_Identifier();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.AbstractTaskImpl <em>Abstract Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.AbstractTaskImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAbstractTask()
		 * @generated
		 */
		EClass ABSTRACT_TASK = eINSTANCE.getAbstractTask();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TASK__NAME = eINSTANCE.getAbstractTask_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TASK__DESCRIPTION = eINSTANCE.getAbstractTask_Description();

		/**
		 * The meta object literal for the '<em><b>Formatted Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TASK__FORMATTED_NAME = eINSTANCE.getAbstractTask_FormattedName();

		/**
		 * The meta object literal for the '<em><b>Formatted Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TASK__FORMATTED_DESCRIPTION = eINSTANCE.getAbstractTask_FormattedDescription();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.TaskFolderImpl <em>Task Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.TaskFolderImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getTaskFolder()
		 * @generated
		 */
		EClass TASK_FOLDER = eINSTANCE.getTaskFolder();

		/**
		 * The meta object literal for the '<em><b>Task Details</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_FOLDER__TASK_DETAILS = eINSTANCE.getTaskFolder_TaskDetails();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.TokenImpl <em>Token</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.TokenImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getToken()
		 * @generated
		 */
		EClass TOKEN = eINSTANCE.getToken();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOKEN__KEY = eINSTANCE.getToken_Key();

		/**
		 * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOKEN__CREATED = eINSTANCE.getToken_Created();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOKEN__USER = eINSTANCE.getToken_User();

		/**
		 * The meta object literal for the '<em><b>Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOKEN__TIMEOUT = eINSTANCE.getToken_Timeout();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.impl.PageImpl
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__LOCATION = eINSTANCE.getPage_Location();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__TITLE = eINSTANCE.getPage_Title();

		/**
		 * The meta object literal for the '{@link com.equinor.modelshare.Access <em>Access</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.equinor.modelshare.Access
		 * @see com.equinor.modelshare.impl.ModelsharePackageImpl#getAccess()
		 * @generated
		 */
		EEnum ACCESS = eINSTANCE.getAccess();

	}

} //ModelsharePackage
