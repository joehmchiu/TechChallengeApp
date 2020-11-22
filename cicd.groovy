pipeline {

  environment {
    repos = "https://github.com/joehmchiu/servian-challenge.git"
    // app   = "https://github.com/joehmchiu/TechChallengeApp.git"
    app   = "git@github.com:joehmchiu/TechChallengeApp.git"
    // repos = "git@github.com:joehmchiu/servian-challenge.git"
    branch = "master"
    cid = "demo"
    workspace = pwd()
    wdir = "/tmp/works"
  }

  agent any
  stages {
    stage('Start Task') {
      steps {
        echo 'Hi, starting to build the App.'
      }
    }
    stage('Update Change') {
      steps {
        sh "sudo rm -rf ${wdir}"
        sh "sudo git clone ${repos} ${wdir}"
      }
    }
    stage('Preload for Deployment') {
      steps {
        echo "${wdir}/auto-cicd-test/servian-installer"
        load "${wdir}/auto-cicd-test/servian-installer"
      }
    }
    stage('Preload for Testing') {
      steps {
        sh "ls -ltrh ${workspace}/"
        echo "${wdir}/auto-cicd-test/crud-tester"
        load "${wdir}/auto-cicd-test/crud-tester"
      }
    }
    stage('Commit Version Change') {
      steps {
        sh "sudo rm -rf ${wdir}"
        sh "sudo git clone ${app} ${wdir}"
        sh "ls -ltrhR"
	sh "sudo /opt/bin/git-push.sh ${wdir}"
        echo "Done!"
      }
    }
  }
}
