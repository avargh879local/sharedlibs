import hudson.util.Secret


// Function to run 'aws --version'
def getAWSVersion(credentialsId) {
    def version = sh(script: "aws --version", returnStdout: true, credentialsId: credentialsId).trim()
    return version
}

// Function to get the number of ECS clusters running
def getECSClusterCount(credentialsId) {
    def clusterCountOutput = sh(script: "aws ecs list-clusters", returnStdout: true, credentialsId: credentialsId).trim()
    if(clusterCountOutput == "0" || clusterCountOutput == "null"){
        println ("There are no active clusters")
    }else{
        println ("clusters are present this is the present value : ${clusterCountOutput}")
    return clusterCountOutput
}
}
