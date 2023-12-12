import hudson.util.Secret


// Function to run 'aws --version'
def getAWSVersion(credentialsId) {
    def version = sh(script: "aws --version", returnStdout: true, credentialsId: credentialsId).trim()
    return version
}

// Function to get the number of ECS clusters running
def getECSClusterCount(credentialsId) {
    def clusterCountOutput = sh(script: "aws ecs list-clusters", returnStdout: true, credentialsId: credentialsId).trim()
    return clusterCountOutput
}

// def getEC2InstancesInSubnet(region, subnetId) {
//     // Run AWS CLI command to describe EC2 instances in the specified region and subnet
//     def instancesOutput = sh(script: "${awsCliPath} ec2 describe-instances --region ${region} --filters Name=subnet-id,Values=${subnetId} --query 'Reservations[].Instances[] | {InstanceId: InstanceId, PublicIpAddress: PublicIpAddress}' --output json", returnStdout: true).trim()

//     return instancesOutput
// }


