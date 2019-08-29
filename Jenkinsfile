pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                if (env.BRANCH_NAME == 'master') {
                    steps.checkout scm
                } else {
                    def config = scm.userRemoteConfigs[0]

                    steps.checkout([
                            $class                           : 'GitSCM',
                            branches                         : scm.branches,
                            doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                            extensions                       : scm.extensions + [[$class: 'LocalBranch', localBranch: '**']],
                            userRemoteConfigs                : [[
                                $class       : 'UserRemoteConfig',
                                url          : config.url,
                                name         : config.name,
                                refspec      : "${config.refspec} +refs/heads/master:refs/remotes/origin/master",
                                credentialsId: config.credentialsId
                            ]]
                    ])
                }
            }
        }

        stage("Tests") {
            steps.sh "./gradlew clean check"
        }
    }
}
