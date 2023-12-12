import hudson.util.Secret

// Function to run 'aws --version'
def getAWSVersion() {
    def version = sh(script: "aws --version", returnStdout: true).trim()
    return version
}

def listEC2Instances(String region) {
    def command = "aws ec2 describe-instances --region ${region} --query 'Reservations[*].Instances[*].[InstanceId, PublicIpAddress]' --output json"
    def output = sh(script: command, returnStdout: true).trim()

    def parsedOutput = new groovy.json.JsonSlurper().parseText(output)
    def instanceDescriptions = []
    int count = 1

    parsedOutput.each { reservation ->
        reservation.each { instance ->
            if (instance[0] != null && instance[1] != null) {
                def description = "${ordinal(count)} instance: ID - ${instance[0]}, IP - ${instance[1]}"
                instanceDescriptions << description
                count++
            }
        }
    }

    return instanceDescriptions
}

// Helper method to convert numbers to ordinal format (1st, 2nd, 3rd, etc.)
def ordinal(int number) {
    def suffixes = ['th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th']
    switch (number % 100) {
        case 11:
        case 12:
        case 13:
            return number + 'th'
        default:
            return number + suffixes[number % 10]
    }
}


