import groovy.json.JsonSlurper

    def createOrUpdateEventBridgeScheduler(Map schedulerDetails) {
        def schedulerName = schedulerDetails.name
        def scheduleExpression = schedulerDetails.scheduleExpression
        def targetArn = schedulerDetails.targetArn

        // Check if the scheduler rule exists
        def checkRuleExistsCommand = "aws events describe-rule --name ${schedulerName}"
        def ruleExists = executeCLICommand(checkRuleExistsCommand).trim()

        if (ruleExists) {
            // Update the existing scheduler
            executeCLICommand("aws events put-rule --name ${schedulerName} --schedule-expression ${scheduleExpression}")
        } else {
            // Create a new scheduler
            executeCLICommand("aws events put-rule --name ${schedulerName} --schedule-expression ${scheduleExpression}")
        }

        // Add target to the rule
        executeCLICommand("aws events put-targets --rule ${schedulerName} --targets Id=1,Arn=${targetArn}")

        println("Scheduler '${schedulerName}' has been created/updated with expression '${scheduleExpression}' and target '${targetArn}'")
    }

    private String executeCLICommand(String command) {
        def proc = command.execute()
        proc.waitFor()
        return proc.in.text
    }
