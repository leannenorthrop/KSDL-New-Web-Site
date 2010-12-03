loadSpecTestTypeClass = { ->
  def doLoad = { -> classLoader.loadClass('grails.plugin.spock.test.GrailsSpecTestType') }
  try {
    doLoad()
  } catch (ClassNotFoundException e) {
    includeTargets << grailsScript("_GrailsCompile") 
    compile()
    doLoad()
  }  
}
 
// 1. add the name of your phase to this variable with this event handler
eventAllTestsStart = {
    phasesToRun << "spock"
    unitTests << specTestTypeClass.newInstance('spock', 'unit')
    integrationTests << specTestTypeClass.newInstance('spock', 'integration')    
}
 
// 2. Create a custom test type
def testTypeName = "spock"
def testDirectory = "unit"
def specTestTypeClass = loadSpecTestTypeClass()
def customTestType = specTestTypeClass.newInstance(testTypeName, testDirectory)
 
// 3. Create a «phase name»Tests variable containing the test type(s)
spockTests = [customTestType]
 
// 4. Create pre and post closures
spockTestPhasePreparation = {
    println "*** Starting Spock Testing" 
}
spockTestPhaseCleanUp = {
    println "*** Finishing Spock Testing"     
}
