import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion(credentialsId) {
    def version = sh(script: "aws --version", returnStdout: true, credentialsId: credentialsId).trim()
    return version
}

// Function to get the number of ECS clusters running
def getECSClusterCount(credentialsId) {
    def clusterCountOutput = sh(script: "aws ecs list-clusters --output json | jq '.clusterArns | length'", returnStdout: true, credentialsId: credentialsId).trim()
        return clusterCountOutput
    }
}
