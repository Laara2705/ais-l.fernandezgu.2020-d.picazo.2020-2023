# Project Management using GitFlow <img src="https://i1.wp.com/blog.knoldus.com/wp-content/uploads/2021/05/git-flow.png?fit=275%2C275&ssl=1.png" alt="codeurjc-icon" width="80" height="75" align="left"/>

<p align="justify">The purpose of this practice is to work with the GitFlow model developed by Vincent Driessen in 2010, providing our application with a framework for branch and version management, aiding in maintaining an organized and efficient structure. It enables development teams to work more collaboratively and help us to ensure the construction of high-quality software. As we progressed with the project, we explored various workflows and incorporated additional functionalities, including the implementation of new features in the sample app. This involved creating essential branches and subjecting our changes to rigorous validation processes to ensure seamless integration with the rest of the project. Furthermore, we adopted the GitFlow model to align our development practices with the day-to-day operations of the business.</p>

## ISSUES <img src="https://www.clker.com/cliparts/9/1/4/0/11954322131712176739question_mark_naught101_02.svg.hi.png" alt="issues-icon" width="40" height="40" align="left"/>

You can click [here](https://github.com/gu4re/ProjectManagement-GitFlow) to access to our repo of GitHub. Also, in [this link](https://books-reviewer-gu4re.cloud.okteto.net/) you can find our Okteto deployment (currently sleeping!). Any issue can be reported <a href="https://github.com/gu4re/gu4re/issues">here<a> including as header <b>"PROJECTMANAGEMENT ISSUE"</b> and I will try to solve it as soon as posible!


## PHASE 1 - WORKFLOWS <img src="https://imgur.com/OIDilkl.png" alt="workflow-icon" width="60" height="60" align="left"/> 
 <p align="right">[May 3, 2023]</p>

### Workflow 1 - Unitary and Integration Testing [](#workflow1)

<p align="justify">We have created the first workflow in the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/develop/.github/workflows">.github/workflows</a> folder, which handles the functionality of running unit and integration tests when we fetch a branch with the "feature" pattern and merge it with the "develop" branch. To achieve this, we have created these branches using both the GitHub website and the following command:</p>

```bash
$ git branch
```

<p align="justify">This allows us to create new branches locally for deployment purposes. Additionally, we have used basic commands to upload files:</p>

```bash
$ git add .
$ git commit -m "msg"
$ git push -u origin "branch"
```

### Workflow 2 - Commit Testing [](#workflow2)

<p align="justify">We have created the second workflow in the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/develop">develop</a> branch, as it was required to run the entire application test suite whenever a <em>git push</em> is made to this branch. Additionally, we have used basic git commands to create and upload files:</p>

```bash
$ git add .
$ git commit -m "msg"
$ git push -u origin "branch"
```
      
<p align="justify">Highlighting the test git push performed to verify that the workflow functions correctly. It is worth mentioning that both workflows, as well as future workflows, have been configured to generate artifacts that store the temporary results of these workflows and, therefore, the test results. This allows us to easily access the artifact in case a workflow fails, helping us identify and troubleshoot any errors.</p>
<p align="right">[May 4, 2023]</p>
 
### Workflow 3 - Nightly Testing [](#workflow3)

<p align="justify">Creating the third workflow required some effort to structure it properly, but we managed to accomplish it. Similar to <a href="https://github.com/gu4re/ProjectManagement-GitFlow/edit/develop/README.md#workflow-2">workflow 2</a>, we have separated the tasks independently and categorized them as requested, such as e2e, rest, etc. Additionally, we have created a Docker image (once all the tests have passed successfully) using the following command:</p>

```bash
# Creation of the docker image
$ mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${{ secrets.DOCKERHUB_USERNAME }}/image_name
# Log in to Docker
$ docker login --username="${{ secrets.DOCKERHUB_USERNAME }}" --password="${{ secrets.DOCKERHUB_TOKEN }}" 
# Push the image
$ docker push "${{ secrets.DOCKERHUB_USERNAME }}"/books-reviewer:dev-$(date +%Y%m%d) 
# Log out
$ docker logout 
```
      
<p align="justify">We are still waiting for the "schedule: crono" part to work. We have tried changing it to a push event to test if our workflow was incorrect, and it worked fine. Therefore, we have scheduled the workflow to run in a few hours to test and see what happens. We have used the same structure as provided in the slides, but without success.</p>

### Workflow 4 - Release Testing [](#workflow4)

<p align="justify">The fourth workflow consists of running unit and integration tests in the test branch <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/release/workflow4-test">release/workflow4-test</a>. This process occurs automatically every time a new commit is detected in that branch. Once again, we have executed the tasks independently, starting with the integration tests and then the unit tests. The results for both tests are stored in artifacts named <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12670222690/artifacts/679363787">integ-results.txt</a> and <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12670222690/artifacts/679363788">unit-results.txt</a> respectively.</p>
<p align="right">[May 5, 2023]</p>

### Workflow 5 - Deployment Testing [](#workflow5)
<p align="justify">The fifth workflow consists of the final workflow, which involves merging a stable release version from the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/develop">develop</a> branch into the production branch <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/master">master</a>. We needed to specify the source as <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/develop">develop</a> as required in the prompt, using a brief bash script, as well as the need to install the Okteto CLI:</p>
      
```yml      
- name: Verifying source branch
        run: |
             if [[ "${{ github.event.pull_request.head.ref }}" != "develop" ]]; then
              echo "The pull request source branch is not 'develop'. Exiting."
              exit 1
             fi
- name: Installing Okteto CLI
  run: |
    curl https://get.okteto.com -sSfL | sh
```
  
<p align="justify">Posteriormente ejecutamos todas las pruebas, construimos la imagen de Docker y la subimos a DocherHub, de la misma forma que hacíamos en el <a href="https://github.com/gu4re/ProjectManagement-GitFlow/edit/develop/README.md#workflow-3">workflow 3</a>, con la diferencia de que ahora utilizando los siguientes comandos:</p>

```bash
$ okteto context use https://cloud.okteto.com --token ${{ secrets.OKTETO_TOKEN }}
$ okteto deploy --wait --build
```
      
<p align="justify">We are able to deploy the Docker image to an Okteto container, providing portability through a URL. Additionally, for the successful deployment on Okteto, we had to configure the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/blob/develop/docker-compose.yml">docker-compose.yml</a> file, which specifies the books-reviewer service as the Docker image to be uploaded to Okteto.
We encountered various issues when running the workflow, especially related to Okteto. We would have liked to automate the image specified in the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/blob/develop/docker-compose.yml">docker-compose.yml</a> file, as no matter how much we specified the Docker username as a GitHub secret in the <em>image</em> attribute, as well as the version through the command line using the following command:</p>
      
```bash
# Command used in various cases for obtaining the POM version
$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
```
<p align="justify">It is not possible as we have seen that it is not supported. Furthermore, we reviewed the GitHub documentation and attempted to automate the process using:</p>
      
```yml  
# Trying to specify it directly
image: ${{ secrets.DOCKERHUB_USERNAME }}/books-reviewer:$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
# Another try using environment
environment:
  VERSION: $(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
  DOCKERUSERNAME: ${{ secrets.DOCKERHUB_USERNAME }}
image: ${DOCKERUSERNAME}/books-reviewer:${VERSION}
```

<p align="justify">However, we were unable to make it work, so we had to write the image directly:</p>

```yml      
version: '3'

services:
  books-reviewer:
    image: gu4re/books-reviewer:0.2.0-SNAPSHOT # Type it literally due to environment problems
    ports:
      - 8080:8080
```

### RELEASE Workflow 3 - Nightly Testing [](#workflow3-2)

<p align="justify">After many attempts and consulting the <a href="https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows#schedule">GitHub documentation</a> and <a href="https://stackoverflow.com/questions/63436541/github-action-workflow-schedule-not-working-on-non-default-branch">StackOverflow</a>, we discovered that for the schedule to work correctly, the workflow needs to be on the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/master">master</a> branch. Therefore, we copied the workflow we had developed on the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/develop">develop</a> branch to the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/tree/master">master</a> branch, achieving a <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4889085337">successful test run</a> at the scheduled time (0:30 UTC, 2:30 in Spain). The Docker image generated from that DockerHub execution can be found <a href="https://hub.docker.com/layers/gu4re/books-reviewer/dev-20230505/images/sha256-e1d2ceee77247a815a6e21a55b1ab6850d3e43e30bc83bf1dd609957a7242c36?context=repo">here</a>.</p>

## PHASE 2 - Developing with (GitFlow/TBD) <img src="https://gitforwindows.org/img/gwindows_logo.png" alt="workflow-icon" width="60" height="60" align="left"/>

<p align="justify">Once the workflows were created and functioning properly, we proceeded to develop the new functionality using (GitFlow or TBD).</p>

```bash
# Clone to a local repo
$ git clone https://github.com/Laara2705/ais-l.fernandezgu.2020-d.picazo.2020-2023.git Practica3-AIS
# Using IntelliJ
$ cd Practica3-AIS
$ idea .
```

<p align="justify">We have determined that the best approach would be to include this new functionality within the <a href="https://github.com/gu4re/ProjectManagement-GitFlow/blob/develop/src/main/java/es/codeurjc/ais/book/BookDetail.java">BookDetail</a> class, specifically within the <em>setDescription(String description)</em> method, which defines the description of a book. We have implemented it as follows:</p>

```java
// In this way, when a description exceeds the limit, we keep the first 947 characters.
// fill the remaining 3 characters up to 950 with '...'                                          
public void setDescription(String description) {
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            this.description = description.substring(0, MAX_DESCRIPTION_LENGTH - 3)
                    .concat("...");
        else
            this.description = description;
    }
```

<p align="justify">Then we added the changes to the <a href="https://github.com/gu4re/ProjectManagement-GitFlow">remote repository</a> in the following way:</p>

```
$ git add .
$ git commit -m "Added the new feature"
$ git push -u origin feature/short-description
```

<p align="justify">Later, we generated a <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/pull/5">pull request</a> to merge the branch that contains the new functionality <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/feature/short-description">feature/short-description</a> into the development branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop</a>. This action triggered a <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4892340725">workflow</a> that successfully validated the action. We merged the branches and integrated the changes into the development branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop</a>. However, when we merged the branches, another <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4892349790">commit workflow</a> was triggered in the development branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop</a>. Upon careful observation, we noticed that although the workflow passed successfully, in the resulting artifacts, specifically in the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12697183866/artifacts/681372753">Unitary Test Results</a>, a minor error occurred in one of the tests, specifically in the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/feature/short-description/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L76">testEXTRA</a>, which was one of the tests we programmed in the initial practice. Nevertheless, the workflow passed successfully as the flow itself did not consider it a critical error and did not affect the normal behavior of the application. However, following the GitFlow model, we will now create a branch called <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/hotfix/short-description">hotfix/short-description</a> and apply a hotfix or fix for this minor error to resolve the issue before releasing, thus providing greater stability to the production version we want to release.
Finally, we applied a small hotfix to our <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/feature/short-description/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L76">testEXTRA</a>, which was a unit test, and managed to solve the problem. We simply needed to avoid the <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html">NullPointerException</a> that occurred when we did not provide a test description when generating a Book within the test, resolving it in the following way:</p>

```java
@Test
@DisplayName("EXTRA: Checking good ID-book passed")
// EXTRA test checking what happens if a good ID is passed
public void testUNIT_EXTRA(){
      /* Given */
      Optional<BookDetail> bookDetailGood;
      // random valid id of an existing book
      when(openLibraryService.getBook("OL259010W"))
                  .thenReturn(
                              new OpenLibraryService.BookData(null, "/work/01",
                              // hello-world is now the new description
                              "hello-world", new Integer[1], new String[]{"HELLO WORLD"}));
      bookDetailGood = bookService.findById("OL259010W");
      Assertions.assertNotSame(Optional.empty(), bookDetailGood,
                  "bookDetail should not be empty");
}
```

## BOOKS REVIEWER v0.2.0 <img src="https://imgur.com/HoDIpCZ.png" alt="workflow-icon" width="60" height="60" align="left"/>
  
<p align="justify">After merging the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/hotfix/short-description">hotfix branch</a> into the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop branch</a>, just as it happened previously with the feature, two workflows were generated. This time, both workflows passed successfully. One workflow was triggered by the pull request and merge into the development branch (<a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4895046354">pull request workflow</a>), and the other workflow was triggered by pushing and committing to the development branch (<a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4895058446">push and commit workflow</a>). Both workflows included the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/develop/src/test/java/es/codeurjc/ais/unitary/ReviewServiceUnitaryTest.java#L83">hotfix</a>. 
To almost complete the GitFlow process, we are going to make some corrections in the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/develop/pom.xml#L7">POM file</a> (specify our names in the artifactID) and refine the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/develop/docker-compose.yml">docker-compose file</a>. Then, we will deploy a stable release of our application from the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop branch</a> with the name <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/release/0.2.0">release/0.2.0</a>. Finally, we uncommented the <em>Generating Selenium Artifact</em> part in all the workflows (previously commented due to the limitation of the free plan for artifact storage), and adjusted the version of our application in the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/release/0.2.0/pom.xml#L8">POM file</a> and the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/blob/release/0.2.0/docker-compose.yml#L5">docker-compose file</a>. We have verified that we do not have any issues with the release thanks to the <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4896277778">executed workflow</a>. Therefore, we are going to merge the release with the development branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop</a> by opening a pull request, and respecting those workflows that will be executed before carrying out this process.
We have just added the new release v0.2.0 to the development branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/develop">develop</a>, which triggered two workflows: one from the pull request (<a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4896412483">pull request workflow</a>) and another from pushing and committing to the development branch (<a href="https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4896425483">push and commit workflow</a>). Finally, we will deploy the application to the production branch <a href="https://github.com/Laara2705/ProjectManagement-GitFlow/tree/master">master</a> and make some final commits to fine-tune and prepare it for possible future development of version v0.3.0.
Lastly, we have integrated the application into the production branch. Here are some final links where you can obtain the artifacts and final images of the application:</p>

<table align="center" border="2" cellspacing="0">
  <td>
  
  - [Deploying Books Reviewer v0.2.0 in master](https://github.com/Laara2705/ProjectManagement-GitFlow/actions/runs/4896503017)
  - [Deployment into Okteto](https://books-reviewer-gu4re.cloud.okteto.net/)
  - [Docker Image v0.2.0](https://hub.docker.com/layers/gu4re/books-reviewer/0.2.0/images/sha256-d912eed88c1b18f38ceb5a9a1bd2b9520f89167a47681fb617d4aaeaed20bb7f?context=repo)
  - [ARTIFACT: Docker Push Results](https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12708186580/artifacts/682184938)
  
  </td>
  <td>
  
  - [ARTIFACT: Integration Testing Results](https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12708186580/artifacts/682184941)
  - [ARTIFACT: REST Testing Results](https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12708186580/artifacts/682184942)
  - [ARTIFACT: Selenium Testing Results](https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12708186580/artifacts/682184944)
  - [ARTIFACT: Unitary Testing Results](https://github.com/Laara2705/ProjectManagement-GitFlow/suites/12708186580/artifacts/682184946)
  
  </td>
</table>
