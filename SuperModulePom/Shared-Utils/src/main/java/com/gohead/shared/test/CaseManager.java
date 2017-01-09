package com.gohead.shared.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gohead.shared.collection.CollectionUtil;
import com.gohead.shared.utils.ConvertStrToObj;

public class CaseManager {
	// private static final Logger LOGGER = LoggerFactory.getLogger(CaseManager.class);
	/**
	 * 每一个测试case标签的name属性
	 */
	private static final String NAME_OF_EVERY_CASE_TR = "case";

	/**
	 * 测试Case的id,自动生成,与html页面的caseId(由js生成)一致.
	 */
	public static int caseId = 1;
	
	private static final String ATTRIBUTE = "attribute";
	private static final String PARAMETERS_ATTRIBUTE = "parameters";
	private static final String IGNORED_ATTRIBUTE = "ignored";
	private static final String EXPECTED_RESULT_ATTRIBUTE = "expectedResult";
	private static final String EXPECTED_OBJ_ATTRIBUTE = "expectedObj";
	/**
	 * 变量名的标签
	 *//*
	private static final String VARIBLE_TAG_NAME = "span";*/
	
	private static List<ParentTest<?>> generateCasesByHtml(String htmlPath) {
		caseId = 1;
		List<ParentTest<?>> ParentTests = new ArrayList<>();
		Document doc = null;
		// 利用系统类加载器的路径寻找html文件
		InputStream is = CaseManager.class.getClassLoader().getResourceAsStream(htmlPath);
		try {
			// 使用UTF-8编码加载网页
			if (is != null) {
				doc = Jsoup.parse(is, "UTF-8", "http://example.com/");
			} else {
				throw new RuntimeException("Unknown file for system classLoader.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 所有的tr元素－即所有的case
		Elements cases = doc.select("tr[name=" + NAME_OF_EVERY_CASE_TR + "]");

		for (Element eleCase : cases) {
			// 每一行的所有单元格
			Elements td = eleCase.getElementsByTag("td");
            ParentTest<Object> itCase = new ParentTest.EmptyParentTest();
            // 单线程环境,不需要同步
			itCase.setCaseId(caseId++);
			// 参数列表
			for (Element eleTd : td) {
				// 单元格的属性名
				String attributeName = eleTd.attr(ATTRIBUTE);
				// 单元格的文本内容
				String innerHtml = eleTd.text();

				/*
				 * // 获取所有变量名,将其替换为对应的值 Elements variables =
				 * eleTd.getElementsByTag(VARIBLE_TAG_NAME); for(Element
				 * variable : variables){ String variableName = variable.text();
				 * innerHtml = innerHtml.replace(variableName,
				 * getValueByClassAndVarible(variableName).toString()); }
				 */
				if (EXPECTED_RESULT_ATTRIBUTE.equals(attributeName)) { // 期望的结果
					itCase.setExpectedResult(Boolean.parseBoolean(innerHtml));
				} else if (PARAMETERS_ATTRIBUTE.equals(attributeName)) { // 查询参数
					itCase.setParameters(ConvertStrToObj.generateParameterList(innerHtml, Object.class));
				} else if (IGNORED_ATTRIBUTE.equals(attributeName)) { // 此测试是否被忽略
					itCase.setIgnored(Boolean.parseBoolean(innerHtml));
				} else if (EXPECTED_OBJ_ATTRIBUTE.equals(attributeName)) {
					itCase.setExpectedObj(ConvertStrToObj.convertStrToObj(innerHtml, Object.class));
				}
			}
			ParentTests.add(itCase);
		} 
		return ParentTests;
	}

	public static Object[][] getParameterArray(String htmlPath) {
		List<ParentTest<?>> parentTests = generateCasesByHtml(htmlPath);
		Object[][] parameterArray = new Object[parentTests.size()][];
		for (int i = 0; i < parameterArray.length; i++) {
			ArrayList<Object> list = new ArrayList<>();
			// 按照构造器的参数顺序来创建每一个case,顺序不能错
			list.add(parentTests.get(i).getCaseId());
			if(parentTests.get(i).isIgnored() != null){ // 没有此元素,取默认
				list.add(parentTests.get(i).isIgnored());
			}
			if(parentTests.get(i).isExpectedResult() != null){ // 没有此元素,取默认
				list.add(parentTests.get(i).isExpectedResult());
			}
			list.add(parentTests.get(i).getExpectedObj());
			List<Object> inputParameters = parentTests.get(i).getParameters();
			for (Object inputParameter : inputParameters) {
				list.add(inputParameter);
			}
			parameterArray[i] = CollectionUtil.convertCollectionToArray(list, Object.class);
		}
		return parameterArray;
	}

	/**
	 * 根据变量名获取对应的值.
	 * 
	 * @param variableName
	 *            name of variable
	 * @return
	 *//*
	private static Object getValueByClassAndVarible(String className, String variableName) {
		Object fieldValue = null;
		if (variableName.contains(".")) {
			variableName = variableName.split("\\.")[1];
		}
		Class<?> clazz = Object.class;
		Field field;
		try {
			field = clazz.getField(variableName);
			fieldValue = field.get(clazz);
		} catch (NoSuchFieldException e) {
			LOGGER.info("Invalid field name: " + variableName);
		} catch (SecurityException e) {
			LOGGER.info("Non-public filed: " + variableName);
		} catch (IllegalArgumentException e) {
			LOGGER.info("Do not get filed value, invalid object: " + clazz);
		} catch (IllegalAccessException e) {
			LOGGER.info("Non-public filed: " + variableName);
		}
		return fieldValue == null ? "null" : fieldValue;
	}*/

	

}
