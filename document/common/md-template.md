## TITLE

### __*Basic Description:*__

DESCRIPTION:

* Non-Number List Item 1
* Non-Number List Item 2

DESCRIPTION:

1. Number List Item 1
2. Number List Item 2

### SUB-TITLE-2

<font style="color:red">
*RED COLOR DESCRIPTION*
</font>

DESCRIPTION

### CODE-SEGMENT

DESCRIPTION, Please refer below example of code.

	<context:component-scan base-package="com.lyra" use-default-filters="false">
		<!-- Include List -->
		<context:include-filter type="regex" expression="com.lyra.milieu.model.*"/>
		<context:include-filter type="regex" expression="com.lyra.milieu.conn.*"/>
		<context:include-filter type="regex" expression="com.lyra.orb.strategy.*"/>
		<!-- Exclude List -->
		<context:exclude-filter type="regex" expression="com.lyra.milieu.res.*"/>
		<context:exclude-filter type="regex" expression="com.lyra.milieu.spring.*"/>
	</context:component-scan>