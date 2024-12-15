module.exports = function(grunt) {
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: {
      prod: ['build/*']
    },
    react: {
      prod: {
        files: {
          'build/<%= pkg.name %>.js': 'src/<%= pkg.name %>.jsx'
        }
      },
      example: {
        files: [
          {
            expand: true,
            cwd: 'example/src/',
            src: ['**/*.jsx'],
            dest: 'example/',
            ext: '.js'
          }
        ]
      }
    },
    jshint: {
      prod: ['Gruntfile.js', 'build/*.js']
    },
    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - '+
          '<%= grunt.template.today("yyyy-mm-dd") %> */\n',
        compress: {
          drop_console: true
        }
      },
      build: {
        src: 'build/<%= pkg.name %>.js',
        dest: 'build/<%= pkg.name %>.min.js'
      }
    },
    copy: {
      prod: {
        expand: true,
        flatten: true,
        src: 'build/react-multiselect.*',
        dest: '.',
        filter: 'isFile',
      }
    }
  });

  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-react');
  
  // Default task(s).
  grunt.registerTask('default', ['clean', 'react', 'uglify', 'copy']);
};
