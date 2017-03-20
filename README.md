# gqas-prototype
gqas prototype demo application

oracle JDBC setup instructions:
1. Download manually and install into local maven repository. 
https://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/

2. Use build.gradle settings
repositories {
	mavenLocal()
    mavenCentral()
}

