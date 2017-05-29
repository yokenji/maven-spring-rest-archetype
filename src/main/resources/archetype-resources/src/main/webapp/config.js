module.exports = {
    dev: {
        EnvironmentConfig : {
            api : '/' + process.argv[3]
        }
    }, 
    prod : {
        EnvironmentConfig : {
            api : ''
        }
    },
    test : {
        EnvironmentConfig : {
            api : ''
        }
    }
}