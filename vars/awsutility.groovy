// Import the necessary classes
import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion(credentialsId) {
    def version = sh(script: "${awsCliPath} --version", returnStatus: true, credentialsId: credentialsId).trim()
    return version
}

// Function to get the number of ECS clusters running
def getECSClusterCount(credentialsId) {
    def clusterCount = sh(script: "${awsCliPath} ecs list-clusters --output json | jq '.clusterArns | length'", returnStatus: true, credentialsId: credentialsId).trim()
    return Integer.parseInt(clusterCount)
}
