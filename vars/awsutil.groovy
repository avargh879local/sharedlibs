import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion() {
    def version = sh(script: "aws --version", returnStdout: true).trim()
    return version
}

def countActiveEC2Instances(String region) {
    def command = "aws ec2 describe-instances --region ${region} --query 'Reservations[*].Instances[?State.Name==`running`]' --output json"
    def output = sh(script: command, returnStdout: true).trim()

    def activeInstancesCount = 0
    def parsedOutput = new groovy.json.JsonSlurper().parseText(output)

    parsedOutput.each { reservation ->
        reservation.each { instance ->
            if (instance.State.Name == 'running') {
                activeInstancesCount++
            }
        }
    }

    return activeInstancesCount
}

