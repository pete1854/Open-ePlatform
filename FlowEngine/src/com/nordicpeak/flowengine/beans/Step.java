package com.nordicpeak.flowengine.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OrderBy;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.populators.PositiveStringIntegerPopulator;
import se.unlogic.standardutils.populators.StringPopulator;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLParser;
import se.unlogic.standardutils.xml.XMLParserPopulateable;
import se.unlogic.standardutils.xml.XMLPopulationUtils;
import se.unlogic.standardutils.xml.XMLValidationUtils;

import com.nordicpeak.flowengine.interfaces.ImmutableStep;

@Table(name = "flowengine_steps")
@XMLElement
public class Step extends GeneratedElementable implements ImmutableStep, XMLParserPopulateable {

	public static final long serialVersionUID = 7072701921778038748L;

	public static final Field FLOW_RELATION = ReflectionUtils.getField(Step.class, "flow");
	public static final Field QUERY_DESCRIPTORS_RELATION = ReflectionUtils.getField(Step.class, "queryDescriptors");

	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer stepID;

	@DAOManaged
	@WebPopulate(required = true, maxLength = 255)
	@XMLElement
	private String name;

	@DAOManaged
	@OrderBy
	@XMLElement
	private Integer sortIndex;

	@DAOManaged(columnName = "flowID")
	@ManyToOne
	private Flow flow;

	@DAOManaged
	@OneToMany
	@XMLElement(fixCase=true)
	private List<QueryDescriptor> queryDescriptors;

	public Integer getStepID() {

		return stepID;
	}

	public void setStepID(Integer stepID) {

		this.stepID = stepID;
	}

	@Override
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public Integer getSortIndex() {

		return sortIndex;
	}

	public void setSortIndex(Integer index) {

		this.sortIndex = index;
	}

	@Override
	public Flow getFlow() {

		return flow;
	}

	public void setFlow(Flow flow) {

		this.flow = flow;
	}

	public List<QueryDescriptor> getQueryDescriptors() {

		return queryDescriptors;
	}

	public void setQueryDescriptors(List<QueryDescriptor> queryDescriptors) {

		this.queryDescriptors = queryDescriptors;
	}

	@Override
	public String toString() {

		return name + " (ID: " + stepID + ")";
	}

	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		List<ValidationError> errors = new ArrayList<ValidationError>();

		this.name = XMLValidationUtils.validateParameter("name", xmlParser, true, 1, 255, StringPopulator.getPopulator(), errors);
		this.sortIndex = XMLValidationUtils.validateParameter("sortIndex", xmlParser, true, PositiveStringIntegerPopulator.getPopulator(), errors);

		this.queryDescriptors = XMLPopulationUtils.populateBeans(xmlParser, "QueryDescriptors/QueryDescriptor", QueryDescriptor.class, errors);

		if(!errors.isEmpty()){

			throw new ValidationException(errors);
		}
	}
}