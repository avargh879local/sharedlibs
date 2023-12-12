// Import the necessary classes
import hudson.util.Secret


// Function to run 'aws --version'
def getAWSVersion() {
    def version = sh(script: "${aws} --version", returnStatus: true).trim()
    return version
}

// Function to get the number of ECS clusters running
def getECSClusterCount() {
    def clusterCount = sh(script: "${awsCliPath} ecs list-clusters --output json | jq '.clusterArns | length'", returnStatus: true).trim()
    return Integer.parseInt(clusterCount)
}
