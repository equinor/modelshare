/**
 */
package com.statoil.modelshare.impl;

import com.statoil.modelshare.AbstractTask;
import com.statoil.modelshare.ModelsharePackage;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.markdown.core.MarkdownLanguage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.impl.AbstractTaskImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AbstractTaskImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AbstractTaskImpl#getFormattedName <em>Formatted Name</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.AbstractTaskImpl#getFormattedDescription <em>Formatted Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractTaskImpl extends MinimalEObjectImpl.Container implements AbstractTask {
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
	 * The default value of the '{@link #getFormattedName() <em>Formatted Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormattedName()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMATTED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormattedName() <em>Formatted Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormattedName()
	 * @generated
	 * @ordered
	 */
	protected String formattedName = FORMATTED_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getFormattedDescription() <em>Formatted Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormattedDescription()
	 * @generated
	 * @ordered
	 */
	protected String formattedDescription = FORMATTED_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractTaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.ABSTRACT_TASK;
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
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ABSTRACT_TASK__NAME, oldName, name));
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
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.ABSTRACT_TASK__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFormattedName() {
		return getName().replaceAll("_", " ");
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFormattedDescription() {
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
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelsharePackage.ABSTRACT_TASK__NAME:
				return getName();
			case ModelsharePackage.ABSTRACT_TASK__DESCRIPTION:
				return getDescription();
			case ModelsharePackage.ABSTRACT_TASK__FORMATTED_NAME:
				return getFormattedName();
			case ModelsharePackage.ABSTRACT_TASK__FORMATTED_DESCRIPTION:
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
			case ModelsharePackage.ABSTRACT_TASK__NAME:
				setName((String)newValue);
				return;
			case ModelsharePackage.ABSTRACT_TASK__DESCRIPTION:
				setDescription((String)newValue);
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
			case ModelsharePackage.ABSTRACT_TASK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelsharePackage.ABSTRACT_TASK__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case ModelsharePackage.ABSTRACT_TASK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelsharePackage.ABSTRACT_TASK__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ModelsharePackage.ABSTRACT_TASK__FORMATTED_NAME:
				return FORMATTED_NAME_EDEFAULT == null ? formattedName != null : !FORMATTED_NAME_EDEFAULT.equals(formattedName);
			case ModelsharePackage.ABSTRACT_TASK__FORMATTED_DESCRIPTION:
				return FORMATTED_DESCRIPTION_EDEFAULT == null ? formattedDescription != null : !FORMATTED_DESCRIPTION_EDEFAULT.equals(formattedDescription);
		}
		return super.eIsSet(featureID);
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
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", formattedName: ");
		result.append(formattedName);
		result.append(", formattedDescription: ");
		result.append(formattedDescription);
		result.append(')');
		return result.toString();
	}

} //AbstractTaskImpl
