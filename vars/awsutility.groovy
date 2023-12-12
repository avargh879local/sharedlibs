import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion() {
    def version = sh(script: "aws --version", returnStdout: true).trim()
    return version
}

def listEC2Instances(String region) {
    // Command to describe instances in the specified region
    // --query filters the output to show only instance IDs and IP addresses
    def command = "aws ec2 describe-instances --region ${region} --query 'Reservations[*].Instances[*].[InstanceId, PublicIpAddress]' --output json"

    // Execute the command and capture the output
    def output = sh(script: command, returnStdout: true).trim()
    // Parse the JSON output to extract instance information
    def instances = []
    def parsedOutput = new groovy.json.JsonSlurper().parseText(output)
    parsedOutput.each { reservation ->
        reservation.each { instance ->
            def instanceInfo = [id: instance[0], ip: instance[1]]
            instances << instanceInfo
        }
    }

    return instances
}

