import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion() {
    def version = sh(script: "aws --version", returnStdout: true).trim()
    return version
}

def countActiveEC2Instances(String region) {
    // AWS CLI command to describe instances in the specified region
    // Filtering for instances with instance-state-name 'running' to consider only active instances
    def command = "aws ec2 describe-instances --region ${region} --query 'Reservations[*].Instances[?State.Name==`running`]' --output json"

    // Execute the command and capture the output
    def output = sh(script: command, returnStdout: true).trim()

    // Parse the JSON output to extract instance information
    def activeInstancesCount = 0
    def parsedOutput = new groovy.json.JsonSlurper().parseText(output)
    parsedOutput.each { reservation ->
        // Each reservation is an array of instances
        activeInstancesCount += reservation.size()
    }

    return activeInstancesCount
}
